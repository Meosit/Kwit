package by.mksn.kwitapi.repository

import by.mksn.kwitapi.entity.*
import by.mksn.kwitapi.entity.support.CategoryStats
import by.mksn.kwitapi.entity.support.CategoryType
import by.mksn.kwitapi.entity.support.IdAssignable
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.io.Serializable
import java.sql.Timestamp
import javax.transaction.Transactional

interface PersonalCrudRepository<E : IdAssignable<ID>, ID : Serializable> {
    fun findByIdAndUserId(id: ID, userId: ID): E?
    fun <S : E> save(entity: S): S
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
interface CategoryRepository :
        PagingAndSortingRepository<Category, Long>,
        PersonalCrudRepository<Category, Long> {
    fun findByUserId(id: Long, pageable: Pageable): List<Category>
    fun findByUserIdAndType(id: Long, type: CategoryType, pageable: Pageable): List<Category>
    fun findCategoryStats(
            @Param("userId") userId: Long,
            @Param("currencyId") currencyId: Long,
            @Param("categoryType") categoryType: CategoryType,
            @Param("startDate") startDate: Timestamp,
            @Param("endDate") endDate: Timestamp
    ): List<CategoryStats>
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

    @Modifying
    @Transactional
    fun deleteByCategoryId(categoryId: Long): Int

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE transaction SET category_id = :newCategoryId WHERE category_id = :oldCategoryId",
            nativeQuery = true)
    fun shiftToNewCategory(
            @Param("newCategoryId") newCategoryId: Long,
            @Param("oldCategoryId") oldCategoryId: Long): Int
}