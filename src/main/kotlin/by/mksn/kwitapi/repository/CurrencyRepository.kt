package by.mksn.kwitapi.repository

import by.mksn.kwitapi.entity.Currency
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface CurrencyRepository : PagingAndSortingRepository<Currency, Long> {
    fun findByCode(code: String): Currency?
}