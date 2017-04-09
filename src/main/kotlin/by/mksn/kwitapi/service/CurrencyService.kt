package by.mksn.kwitapi.service

import by.mksn.kwitapi.entity.Currency

interface CurrencyService {

    fun create(currency: Currency): Currency

    fun findById(id: Long): Currency?

    fun findAll(): List<Currency>

    fun findByCode(code: String): Currency

    fun update(id: Long, currency: Currency): Currency

    fun delete(id: Long)
}