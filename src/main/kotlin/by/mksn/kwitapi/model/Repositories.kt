package by.mksn.kwitapi.model

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {
    override fun findAll(): List<User>
    fun findByEmail(email: String): User?
}

@Repository
interface CurrencyRepository : CrudRepository<Currency?, Long> {
    fun findByCode(code: String): Currency?
}

@Repository
interface WalletRepository : CrudRepository<Wallet, Long> {
    override fun findAll(): List<Wallet>
    fun findByUserId(id: Long): List<Wallet>
}

@Repository
interface CategoryRepository : CrudRepository<Category, Long> {
    override fun findAll(): List<Category>
    fun findByUserId(id: Long): List<Category>
    fun findByUserIdAndIsIncome(id: Long, isIncome: Boolean): List<Category>
}

@Repository
interface RemittanceRepository : PagingAndSortingRepository<Remittance, Long> {
    fun findByUserId(id: Long): List<Remittance>
    fun findByUserId(id: Long, pageable: Pageable): List<Remittance>
}

@Repository
interface TransactionRepository : PagingAndSortingRepository<Transaction, Long> {
    fun findByUserId(id: Long): List<Transaction>
    fun findByUserId(id: Long, pageable: Pageable): List<Transaction>
}