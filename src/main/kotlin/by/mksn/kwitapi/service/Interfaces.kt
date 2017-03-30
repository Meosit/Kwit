package by.mksn.kwitapi.service

import by.mksn.kwitapi.controller.RegistrationDetails
import by.mksn.kwitapi.model.*
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service


@Service
interface UserService : CrudService<User, Long> {
    fun findByEmail(username: String): User?
    fun register(registrationDetails: RegistrationDetails)
}

@Service
interface CurrencyService : CrudService<Currency, Long> {
    fun findAll(): List<Currency>
    fun findByCode(code: String): Currency
}

@Service
interface WalletRepository : CrudService<Wallet, Long> {
    fun findAll(): List<Wallet>
    fun findByUserId(id: Long): List<Wallet>
}

@Service
interface CategoryRepository : CrudService<Category, Long> {
    fun findAll(): List<Category>
    fun findByUserId(id: Long): List<Category>
    fun findByUserIdAndIsIncome(id: Long, isIncome: Boolean): List<Category>
}

@Service
interface RemittanceRepository : CrudService<Remittance, Long> {
    fun findByUserId(id: Long): List<Remittance>
    fun findByUserId(id: Long, pageable: Pageable): List<Remittance>
}

@Service
interface TransactionRepository : CrudService<Transaction, Long> {
    fun findByUserId(id: Long): List<Transaction>
    fun findByUserId(id: Long, pageable: Pageable): List<Transaction>
}