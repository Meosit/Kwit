package by.mksn.kwitapi.service

import by.mksn.kwitapi.entity.Transaction
import org.springframework.data.domain.Pageable

interface TransactionService : CrudService<Transaction, Long> {
    fun findByUserId(id: Long, pageable: Pageable): List<Transaction>
}