package by.mksn.kwit.service.impl

import by.mksn.kwit.entity.Remittance
import by.mksn.kwit.repository.RemittanceRepository
import by.mksn.kwit.repository.WalletRepository
import by.mksn.kwit.service.RemittanceService
import by.mksn.kwit.service.exception.ServiceException
import by.mksn.kwit.support.RestErrorMessage
import by.mksn.kwit.support.add
import by.mksn.kwit.support.throwIfNeed
import by.mksn.kwit.support.wrapJPACall
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
            errors.add("Field 'walletAcceptor'", "Nested field 'id' is not specified")
        }
        if (entity.walletDonor?.id == null) {
            errors.add("Field 'walletDonor'", "Nested field 'id' is not specified")
        }
        errors.throwIfNeed()
    }

    override fun findByIdAndUserId(id: Long, userId: Long): Remittance?
            = wrapJPACall { remittanceRepository.findByIdAndUserId(id, userId) }

    override fun findAllByUserId(userId: Long, pageable: Pageable): Page<Remittance>
            = wrapJPACall { remittanceRepository.findByUserId(userId, pageable) }

}