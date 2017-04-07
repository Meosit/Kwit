package by.mksn.kwitapi.service

import by.mksn.kwitapi.entity.Currency

interface CurrencyService : CrudService<Currency, Long> {
    fun findAll(): List<Currency>
    fun findByCode(code: String): Currency
}