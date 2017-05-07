package by.mksn.kwitapi.repository

import by.mksn.kwitapi.entity.Transaction
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface TransactionRepository :
        PagingAndSortingRepository<Transaction, Long>,
        PersonalCrudRepository<Transaction, Long> {

    @Query("""SELECT t FROM Transaction t
                INNER JOIN FETCH t.wallet w
                INNER JOIN FETCH w.currency
                INNER JOIN FETCH t.category
                WHERE  (t.id = :id) AND (t.userId = :userId)""")
    override fun findByIdAndUserId(@Param("id") id: Long, @Param("userId") userId: Long): Transaction?

    @Query("""SELECT t FROM Transaction t
                INNER JOIN FETCH t.wallet w
                INNER JOIN FETCH w.currency
                INNER JOIN FETCH t.category
                WHERE t.userId = :id""")
    fun findByUserIdOrderByDateDescIdDesc(@Param("id") id: Long, pageable: Pageable): List<Transaction>

    @Modifying
    @Transactional
    fun deleteByCategoryId(categoryId: Long): Int

    @Modifying
    @Transactional
    fun deleteByWalletId(id: Long): Int

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE transaction SET category_id = :newCategoryId WHERE category_id = :oldCategoryId",
            nativeQuery = true)
    fun shiftToNewCategory(
            @Param("newCategoryId") newCategoryId: Long,
            @Param("oldCategoryId") oldCategoryId: Long): Int

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE transaction SET wallet_id = :newWalletId WHERE wallet_id = :oldWalletId",
            nativeQuery = true)
    fun shiftToNewWallet(@Param("newWalletId") newWalletId: Long,
                         @Param("oldWalletId") oldWalletId: Long): Int
}