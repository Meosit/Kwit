package by.mksn.kwitapi.service.impl

import by.mksn.kwitapi.add
import by.mksn.kwitapi.controller.exception.RestErrorMessage
import by.mksn.kwitapi.entity.Remittance
import by.mksn.kwitapi.isAny
import by.mksn.kwitapi.repository.RemittanceRepository
import by.mksn.kwitapi.repository.WalletRepository
import by.mksn.kwitapi.service.RemittanceService
import by.mksn.kwitapi.service.exception.ServiceBadRequestException
import by.mksn.kwitapi.wrapJPACall
import org.springframework.data.domain.Pageable

open class RemittanceServiceImpl(
        private val remittanceRepository: RemittanceRepository,
        private val walletRepository: WalletRepository
) : AbstractCrudService<Remittance>(remittanceRepository), RemittanceService {

    override fun checkValidNestedEntitiesIfNeed(entity: Remittance) {
        val errors = mutableListOf<RestErrorMessage>()
        if (entity.walletAcceptor?.id == null) {
            errors.add("Field 'walletAcceptor'" to "Nested field 'id' is not specified")
        }
        if (entity.walletDonor?.id == null) {
            errors.add("Field 'walletDonor'" to "Nested field 'id' is not specified")
        }
        errors.isAny { throw ServiceBadRequestException(it) }
    }

    override fun findAllByUserId(userId: Long, pageable: Pageable): List<Remittance> = wrapJPACall {
        remittanceRepository.findByUserIdOrderByDateDescIdDesc(userId, pageable)
    }

}