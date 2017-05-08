package by.mksn.kwit.service.impl

import by.mksn.kwit.entity.Transaction
import by.mksn.kwit.repository.TransactionRepository
import by.mksn.kwit.service.TransactionService
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
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = arrayOf(ServiceException::class))
open class TransactionServiceImpl(
        private val transactionRepository: TransactionRepository
) : AbstractCrudService<Transaction>(transactionRepository), TransactionService {

    @Transactional(propagation = Propagation.SUPPORTS)
    override fun checkValidNestedEntitiesIfNeed(entity: Transaction) {
        val errors = mutableListOf<RestErrorMessage>()
        if (entity.wallet?.id == null) {
            errors.add("Field 'wallet'", "Nested field 'id' is not specified")
        }
        if (entity.category?.id == null) {
            errors.add("Field 'category'", "Nested field 'id' is not specified")
        }
        errors.throwIfNeed()
    }

    override fun findByIdAndUserId(id: Long, userId: Long): Transaction?
            = wrapJPACall { transactionRepository.findByIdAndUserId(id, userId) }

    override fun findAllByUserId(userId: Long, pageable: Pageable): Page<Transaction>
            = wrapJPACall { transactionRepository.findByUserId(userId, pageable) }

}