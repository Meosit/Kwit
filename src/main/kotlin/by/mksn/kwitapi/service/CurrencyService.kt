package by.mksn.kwitapi.service

import by.mksn.kwitapi.entity.Currency
import org.springframework.data.domain.Pageable

interface CurrencyService {

    fun create(currency: Currency): Currency

    fun findById(id: Long): Currency?

    fun findAll(pageable: Pageable): List<Currency>

    fun findByCode(code: String): Currency?

    fun update(id: Long, currency: Currency): Currency

    fun delete(id: Long)
}