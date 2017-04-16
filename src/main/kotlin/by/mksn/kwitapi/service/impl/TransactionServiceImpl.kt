package by.mksn.kwitapi.service.impl

import by.mksn.kwitapi.entity.Transaction
import by.mksn.kwitapi.repository.TransactionRepository
import by.mksn.kwitapi.service.TransactionService
import by.mksn.kwitapi.wrapJPACall
import org.springframework.data.domain.Pageable

open class TransactionServiceImpl(
        private val transactionRepository: TransactionRepository
) : AbstractCrudService<Transaction>(transactionRepository), TransactionService {

    override fun findAllByUserId(userId: Long, pageable: Pageable): List<Transaction> = wrapJPACall {
        transactionRepository.findByUserIdOrderByDateDescIdDesc(userId, pageable)
    }

}