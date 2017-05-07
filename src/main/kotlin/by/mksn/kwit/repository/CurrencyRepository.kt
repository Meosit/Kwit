package by.mksn.kwit.repository

import by.mksn.kwit.entity.Currency
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface CurrencyRepository : PagingAndSortingRepository<Currency, Long> {
    fun findByCode(code: String): Currency?
}