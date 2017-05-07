package by.mksn.kwitapi.service.impl

import by.mksn.kwitapi.entity.Remittance
import by.mksn.kwitapi.repository.RemittanceRepository
import by.mksn.kwitapi.repository.WalletRepository
import by.mksn.kwitapi.service.RemittanceService
import by.mksn.kwitapi.service.exception.ServiceBadRequestException
import by.mksn.kwitapi.service.exception.ServiceException
import by.mksn.kwitapi.support.RestErrorMessage
import by.mksn.kwitapi.support.add
import by.mksn.kwitapi.support.isAny
import by.mksn.kwitapi.support.wrapJPACall
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = arrayOf(ServiceException::class)) class RemittanceServiceImpl(
        private val remittanceRepository: RemittanceRepository,
        private val walletRepository: WalletRepository
) : AbstractCrudService<Remittance>(remittanceRepository), RemittanceService {

    @Transactional(propagation = Propagation.SUPPORTS)
    override fun checkValidNestedEntitiesIfNeed(entity: Remittance) {
        val errors = mutableListOf<RestErrorMessage>()
        if (entity.walletAcceptor?.id == null) {
            errors.add("Field 'walletAcceptor'" to "Nested field 'id' is not specified")
        }
        if (entity.walletDonor?.id == null) {
            errors.add("Field 'walletDonor'" to "Nested field 'id' is not specified")
        }
        errors.isAny { throw ServiceBadRequestException(this) }
    }

    override fun findByIdAndUserId(id: Long, userId: Long): Remittance?
            = wrapJPACall { remittanceRepository.findByIdAndUserId(id, userId) }

    override fun findAllByUserId(userId: Long, pageable: Pageable): Page<Remittance>
            = wrapJPACall { remittanceRepository.findByUserId(userId, pageable) }

}