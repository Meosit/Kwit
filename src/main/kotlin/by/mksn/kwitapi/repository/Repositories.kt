package by.mksn.kwitapi.repository

import by.mksn.kwitapi.entity.*
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.io.Serializable

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
        PagingAndSortingRepository<Wallet, Long>, PersonalCrudRepository<Wallet, Long> {
    fun findByUserIdOrderByIsSavingAsc(id: Long, pageable: Pageable): List<Wallet>
}

@Repository
interface CategoryRepository :
        PagingAndSortingRepository<Category, Long>, PersonalCrudRepository<Category, Long> {
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
interface RemittanceRepository :
        PagingAndSortingRepository<Remittance, Long>, PersonalCrudRepository<Remittance, Long> {
    fun findByUserIdOrderByDateDescIdDesc(id: Long, pageable: Pageable): List<Remittance>
}

@Repository
interface TransactionRepository :
        PagingAndSortingRepository<Transaction, Long>, PersonalCrudRepository<Transaction, Long> {
    fun findByUserIdOrderByDateDescIdDesc(id: Long, pageable: Pageable): List<Transaction>
}