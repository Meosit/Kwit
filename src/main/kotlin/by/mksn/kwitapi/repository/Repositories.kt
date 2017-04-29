package by.mksn.kwitapi.repository

import by.mksn.kwitapi.entity.*
import by.mksn.kwitapi.entity.support.CategoryStats
import by.mksn.kwitapi.entity.support.CategoryType
import by.mksn.kwitapi.entity.support.IdAssignable
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.io.Serializable
import java.math.BigDecimal
import javax.persistence.ColumnResult
import javax.persistence.ConstructorResult
import javax.persistence.NamedNativeQuery
import javax.persistence.SqlResultSetMapping

interface PersonalCrudRepository<E : IdAssignable<ID>, in ID : Serializable> {
    fun findByIdAndUserId(id: ID, userId: ID): E?
    fun <S : E?> save(entity: E): S
    fun delete(id: ID)
}

@Repository
interface UserRepository : PagingAndSortingRepository<User, Long> {
    fun findByEmail(email: String): User?
}

@Repository
interface CurrencyRepository : PagingAndSortingRepository<Currency, Long> {
    fun findByCode(code: String): Currency?
}

@Repository
interface WalletRepository :
        PagingAndSortingRepository<Wallet, Long>,
        PersonalCrudRepository<Wallet, Long> {
    fun findByUserIdOrderByTypeAsc(id: Long, pageable: Pageable): List<Wallet>
}

@Repository
@SqlResultSetMapping(name = "categoryStats", classes = arrayOf(
        ConstructorResult(targetClass = CategoryStats::class, columns = arrayOf(
                ColumnResult(name = "out_category_id", type = Long::class),
                ColumnResult(name = "out_category_name", type = String::class),
                ColumnResult(name = "out_currency_code", type = String::class),
                ColumnResult(name = "out_currency_symbol", type = String::class),
                ColumnResult(name = "out_currency_is_prefix", type = String::class),
                ColumnResult(name = "out_sum_for_category", type = BigDecimal::class),
                ColumnResult(name = "out_percent_of_all", type = BigDecimal::class),
                ColumnResult(name = "out_count_for_category", type = Int::class)
        ))
))
@NamedNativeQuery(name = "Category.findCategoryStats",
        query = "",
        resultSetMapping = "categoryStats")
interface CategoryRepository :
        PagingAndSortingRepository<Category, Long>,
        PersonalCrudRepository<Category, Long> {
    fun findByUserId(id: Long, pageable: Pageable): List<Category>
    fun findByUserIdAndType(id: Long, type: CategoryType, pageable: Pageable): List<Category>

    /*@Query("select new by.mksn.kwitapi.entity.support.CategoryStats(t.category.id, ?2, sum(t.sum), count(t)) " +
            "from Transaction t " +
            "where (t.category.type = ?1) and (t.date between ?3 and ?4) and (t.wallet.currency.id = ?2))")
    fun calculateCategoryStats(
            type: Category.CategoryType, currencyId: Long,
            startDate: Timestamp, endDate: Timestamp
    ): List<CategoryStats>*/
}

@Repository
interface RemittanceRepository :
        PagingAndSortingRepository<Remittance, Long>,
        PersonalCrudRepository<Remittance, Long> {
    fun findByUserIdOrderByDateDescIdDesc(id: Long, pageable: Pageable): List<Remittance>
}

@Repository
interface TransactionRepository :
        PagingAndSortingRepository<Transaction, Long>,
        PersonalCrudRepository<Transaction, Long> {
    fun findByUserIdOrderByDateDescIdDesc(id: Long, pageable: Pageable): List<Transaction>
}