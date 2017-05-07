package by.mksn.kwit.service

import by.mksn.kwit.entity.Currency
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CurrencyService {

    fun create(currency: Currency): Currency

    fun findById(id: Long): Currency?

    fun findAll(pageable: Pageable): Page<Currency>

    fun findAll(): Iterable<Currency>

    fun findByCode(code: String): Currency?

    fun update(id: Long, currency: Currency): Currency

    fun delete(id: Long)
}