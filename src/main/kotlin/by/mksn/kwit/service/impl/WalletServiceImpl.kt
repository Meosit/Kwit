package by.mksn.kwit.service.impl

import by.mksn.kwit.entity.Wallet
import by.mksn.kwit.entity.support.CostForecast
import by.mksn.kwit.repository.TransactionRepository
import by.mksn.kwit.repository.WalletRepository
import by.mksn.kwit.service.UserService
import by.mksn.kwit.service.WalletService
import by.mksn.kwit.service.exception.ServiceBadRequestException
import by.mksn.kwit.service.exception.ServiceException
import by.mksn.kwit.service.exception.ServiceNotFoundException
import by.mksn.kwit.support.*
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = arrayOf(ServiceException::class))
class WalletServiceImpl(
        private val walletRepository: WalletRepository,
        private val transactionRepository: TransactionRepository,
        private val userService: UserService
) : AbstractCrudService<Wallet>(walletRepository), WalletService {

    companion object {
        private val logger = LoggerFactory.getLogger(WalletServiceImpl::class.java)!!
    }

    override fun checkValidNestedEntitiesIfNeed(entity: Wallet) {
        val errors = mutableListOf<RestErrorMessage>()
        if (entity.currency?.id == null) {
            errors.add("Field 'currency'", "Nested field 'id' is not specified")
        }
        errors.throwIfNeed()
    }

    override fun findByIdAndUserId(id: Long, userId: Long): Wallet? =
            wrapJPACall { walletRepository.findByIdAndUserId(id, userId) }

    override fun findAllByUserId(userId: Long): List<Wallet> =
            wrapJPACall { walletRepository.findByUserId(userId) }

    override fun findAllByUserId(userId: Long, pageable: Pageable): Page<Wallet> =
            wrapJPACall { walletRepository.findByUserId(userId, pageable) }

    override fun delete(id: Long, userId: Long): Unit? {
        checkPersonalVisibility(userId, id)
        if (id == userId) throw ServiceBadRequestException("Error" to "Cannot shift transactions to delete wallet")
        val affected = wrapJPACall { transactionRepository.deleteByWalletId(id) }
        logger.info("$affected transactions deleted from wallet[$id]")
        val isSuccess: Unit? = wrapJPAModifyingCall { walletRepository.delete(id) }
        if (isSuccess != null) logger.info("Wallet[$id] deleted")
        return isSuccess
    }

    override fun softDelete(id: Long, newId: Long, userId: Long): Unit? {
        checkPersonalVisibility(userId, id)
        val newWallet = wrapJPACall { walletRepository.findByIdAndUserId(newId, userId) }
        val oldWallet = wrapJPACall { walletRepository.findByIdAndUserId(id, userId) }
        newWallet ?: throw ServiceNotFoundException("New wallet" to "Wallet not found.")
        oldWallet ?: throw ServiceNotFoundException("Old wallet" to "Wallet not found.")
        if (oldWallet.currency?.id != newWallet.currency?.id) {
            throw ServiceBadRequestException("Invalid currency" to "Wallets must have same currencies")
        }
        val affected = wrapJPACall { transactionRepository.shiftToNewWallet(newId, id) }
        logger.info("$affected transactions shifted from wallet[$id] to wallet[$newId, ${newWallet.name}]")
        val isSuccess: Unit? = wrapJPAModifyingCall { walletRepository.delete(id) }
        if (isSuccess != null) logger.info("Wallet[$id] deleted")
        return isSuccess
    }

    override fun calculateCostForecast(userId: Long): CostForecast? {
        val errors = mutableListOf<RestErrorMessage>()
        val salaryInfo = userService.findSalaryInfo(userId)
        salaryInfo ?: return null
        val average = walletRepository.calculateSumForNormal(salaryInfo.salaryCurrencyCode!!)
        average ?: errors.add("Error", "Cannot calculate daily sum")
        val prediction = transactionRepository.calculateMovingAveragePrediction(userId,
                salaryInfo.salaryCurrencyCode, PREDICTION_LOOKUP_DAYS)
        prediction ?: errors.add("Error", "Cannot calculate average prediction")
        errors.throwIfNeed()

        val calendar = Calendar.getInstance()
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val salaryDay = if (salaryInfo.salaryDay!! > maxDayOfMonth) maxDayOfMonth else salaryInfo.salaryDay
        val daysTillSalary = if (dayOfMonth >= salaryDay)
            maxDayOfMonth - dayOfMonth + salaryDay else salaryDay - dayOfMonth
        return CostForecast(
                dailySumTillSalary = average!!.divide(BigDecimal(daysTillSalary), RoundingMode.FLOOR).setScale(4),
                actualCosts = prediction!!,
                daysTillSalary = daysTillSalary
        )
    }

}