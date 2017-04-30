package by.mksn.kwitapi.service.impl

import by.mksn.kwitapi.add
import by.mksn.kwitapi.controller.exception.RestErrorMessage
import by.mksn.kwitapi.entity.Transaction
import by.mksn.kwitapi.isAny
import by.mksn.kwitapi.repository.TransactionRepository
import by.mksn.kwitapi.service.TransactionService
import by.mksn.kwitapi.service.exception.ServiceBadRequestException
import by.mksn.kwitapi.wrapJPACall
import org.springframework.data.domain.Pageable

open class TransactionServiceImpl(
        private val transactionRepository: TransactionRepository
) : AbstractCrudService<Transaction>(transactionRepository), TransactionService {

    override fun checkValidNestedEntitiesIfNeed(entity: Transaction) {
        val errors = mutableListOf<RestErrorMessage>()
        if (entity.wallet?.id == null) {
            errors.add("Field 'wallet'" to "Nested field 'id' is not specified")
        }
        if (entity.category?.id == null) {
            errors.add("Field 'category'" to "Nested field 'id' is not specified")
        }
        errors.isAny { throw ServiceBadRequestException(it) }
    }

    override fun findAllByUserId(userId: Long, pageable: Pageable): List<Transaction> = wrapJPACall {
        transactionRepository.findByUserIdOrderByDateDescIdDesc(userId, pageable)
    }

}