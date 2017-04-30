package by.mksn.kwitapi.service.impl

import by.mksn.kwitapi.add
import by.mksn.kwitapi.controller.exception.RestErrorMessage
import by.mksn.kwitapi.entity.Wallet
import by.mksn.kwitapi.isAny
import by.mksn.kwitapi.repository.TransactionRepository
import by.mksn.kwitapi.repository.WalletRepository
import by.mksn.kwitapi.service.WalletService
import by.mksn.kwitapi.service.exception.ServiceBadRequestException
import by.mksn.kwitapi.wrapJPACall
import org.springframework.data.domain.Pageable

open class WalletServiceImpl(
        private val walletRepository: WalletRepository,
        private val transactionRepository: TransactionRepository
) : AbstractCrudService<Wallet>(walletRepository), WalletService {

    override fun checkValidNestedEntitiesIfNeed(entity: Wallet) {
        val errors = mutableListOf<RestErrorMessage>()
        if (entity.currency?.id == null) {
            errors.add("Field 'currency'" to "Nested field 'id' is not specified")
        }
        errors.isAny { throw ServiceBadRequestException(it) }
    }

    override fun findAllByUserId(userId: Long, pageable: Pageable): List<Wallet> = wrapJPACall {
        walletRepository.findByUserIdOrderByTypeAsc(userId, pageable)
    }
}