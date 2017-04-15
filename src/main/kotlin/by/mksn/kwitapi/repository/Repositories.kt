package by.mksn.kwitapi.repository

import by.mksn.kwitapi.entity.*
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {
    fun findByEmail(email: String): User?
}

@Repository
interface CurrencyRepository : CrudRepository<Currency?, Long> {
    override fun findAll(): List<Currency>
    fun findByCode(code: String): Currency?
}

@Repository
interface WalletRepository : PagingAndSortingRepository<Wallet, Long> {
    fun findByIdAndUserId(id: Long, userId: Long): Wallet?
    fun findByUserIdOrderByIsSavingDesc(id: Long, pageable: Pageable): List<Wallet>
}

@Repository
interface CategoryRepository : PagingAndSortingRepository<Category, Long> {
    fun findByIdAndUserId(id: Long, userId: Long): Category?
    fun findByUserId(id: Long, pageable: Pageable): List<Category>
    fun findByUserIdAndType(id: Long, type: Category.Type, pageable: Pageable): List<Category>

    /*@Query("select new by.mksn.kwitapi.entity.CategoryStats(t.category.id, ?2, sum(t.sum), count(t)) " +
            "from Transaction t " +
            "where (t.category.type = ?1) and (t.date between ?3 and ?4) and (t.wallet.currency.id = ?2))")
    fun calculateCategoryStats(
            type: Category.Type, currencyId: Long,
            startDate: Timestamp, endDate: Timestamp
    ): List<CategoryStats>*/
}

@Repository
interface RemittanceRepository : PagingAndSortingRepository<Remittance, Long> {
    fun findByIdAndUserId(id: Long, userId: Long): Remittance?
    fun findByUserIdOrderByDateDescIdDesc(id: Long, pageable: Pageable): List<Remittance>
}

@Repository
interface TransactionRepository : PagingAndSortingRepository<Transaction, Long> {
    fun findByIdAndUserId(id: Long, userId: Long): Transaction?
    fun findByUserIdOrderByDateDescIdDesc(id: Long, pageable: Pageable): List<Transaction>
}