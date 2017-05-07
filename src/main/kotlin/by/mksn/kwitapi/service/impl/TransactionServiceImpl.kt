package by.mksn.kwitapi.service.impl

import by.mksn.kwitapi.entity.Transaction
import by.mksn.kwitapi.repository.TransactionRepository
import by.mksn.kwitapi.service.TransactionService
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
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = arrayOf(ServiceException::class))
open class TransactionServiceImpl(
        private val transactionRepository: TransactionRepository
) : AbstractCrudService<Transaction>(transactionRepository), TransactionService {

    @Transactional(propagation = Propagation.SUPPORTS)
    override fun checkValidNestedEntitiesIfNeed(entity: Transaction) {
        val errors = mutableListOf<RestErrorMessage>()
        if (entity.wallet?.id == null) {
            errors.add("Field 'wallet'" to "Nested field 'id' is not specified")
        }
        if (entity.category?.id == null) {
            errors.add("Field 'category'" to "Nested field 'id' is not specified")
        }
        errors.isAny { throw ServiceBadRequestException(this) }
    }

    override fun findByIdAndUserId(id: Long, userId: Long): Transaction?
            = wrapJPACall { transactionRepository.findByIdAndUserId(id, userId) }

    override fun findAllByUserId(userId: Long, pageable: Pageable): Page<Transaction>
            = wrapJPACall { transactionRepository.findByUserId(userId, pageable) }

}