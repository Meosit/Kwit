package by.mksn.kwit.repository

import by.mksn.kwit.entity.Transaction
import org.springframework.data.domain.Page
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
                WHERE t.userId = :id
                ORDER BY t.date DESC, t.id ASC""",
            countQuery = "SELECT COUNT(t) FROM Transaction t WHERE t.userId = :id")
    fun findByUserId(@Param("id") id: Long, pageable: Pageable): Page<Transaction>

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Transaction t WHERE t.category.id = :id")
    fun deleteByCategoryId(@Param("id") categoryId: Long): Int

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Transaction t WHERE t.wallet.id = :id")
    fun deleteByWalletId(@Param("id") id: Long): Int

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