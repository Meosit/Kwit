package by.mksn.kwitapi.service.impl

import by.mksn.kwitapi.entity.Wallet
import by.mksn.kwitapi.repository.TransactionRepository
import by.mksn.kwitapi.repository.WalletRepository
import by.mksn.kwitapi.service.WalletService
import by.mksn.kwitapi.service.exception.ServiceBadRequestException
import by.mksn.kwitapi.service.exception.ServiceException
import by.mksn.kwitapi.service.exception.ServiceNotFoundException
import by.mksn.kwitapi.support.*
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = arrayOf(ServiceException::class))
class WalletServiceImpl(
        private val walletRepository: WalletRepository,
        private val transactionRepository: TransactionRepository
) : AbstractCrudService<Wallet>(walletRepository), WalletService {

    companion object {
        private val logger = LoggerFactory.getLogger(WalletServiceImpl::class.java)!!
    }

    override fun checkValidNestedEntitiesIfNeed(entity: Wallet) {
        val errors = mutableListOf<RestErrorMessage>()
        if (entity.currency?.id == null) {
            errors.add("Field 'currency'" to "Nested field 'id' is not specified")
        }
        errors.isAny { throw ServiceBadRequestException(this) }
    }

    override fun findByIdAndUserId(id: Long, userId: Long): Wallet?
            = wrapJPACall { walletRepository.findByIdAndUserId(id, userId) }

    override fun findAllByUserId(userId: Long, pageable: Pageable): Page<Wallet>
            = wrapJPACall { walletRepository.findByUserId(userId, pageable) }

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
        newWallet ?: throw ServiceNotFoundException("New wallet" to "Wallet with id '$newId' not found.")
        val affected = wrapJPACall { transactionRepository.shiftToNewWallet(newId, id) }
        logger.info("$affected transactions shifted from wallet[$id] to wallet[$newId, ${newWallet.name}]")
        val isSuccess: Unit? = wrapJPAModifyingCall { walletRepository.delete(id) }
        if (isSuccess != null) logger.info("Wallet[$id] deleted")
        return isSuccess
    }

}