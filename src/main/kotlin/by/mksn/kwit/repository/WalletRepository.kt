package by.mksn.kwit.repository

import by.mksn.kwit.entity.Wallet
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
interface WalletRepository :
        PagingAndSortingRepository<Wallet, Long>,
        PersonalCrudRepository<Wallet, Long> {

    @Query("SELECT w FROM Wallet w INNER JOIN FETCH w.currency WHERE (w.id = :id) AND (w.userId = :userId)")
    override fun findByIdAndUserId(@Param("id") id: Long, @Param("userId") userId: Long): Wallet?

    @Query("SELECT w FROM Wallet w INNER JOIN FETCH w.currency WHERE w.userId = :id ORDER BY w.type ASC, w.id ASC",
            countQuery = "SELECT COUNT(w) FROM Wallet w WHERE w.userId = :id")
    fun findByUserId(@Param("id") id: Long, pageable: Pageable): Page<Wallet>

    @Query("SELECT w FROM Wallet w INNER JOIN FETCH w.currency WHERE w.userId = :id ORDER BY w.type ASC, w.id ASC")
    fun findByUserId(@Param("id") id: Long): List<Wallet>

    @Query("""SELECT SUM(w.balance) FROM Wallet w
                INNER JOIN w.currency c
                WHERE w.type = 'NORMAL'
                AND c.code = :currencyCode""")
    fun calculateSumForNormal(@Param("currencyCode") currencyCode: String): BigDecimal?
}