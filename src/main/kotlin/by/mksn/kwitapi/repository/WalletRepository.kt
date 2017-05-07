package by.mksn.kwitapi.repository

import by.mksn.kwitapi.entity.Wallet
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface WalletRepository :
        PagingAndSortingRepository<Wallet, Long>,
        PersonalCrudRepository<Wallet, Long> {

    @Query("SELECT w FROM Wallet w INNER JOIN FETCH w.currency WHERE (w.id = :id) AND (w.userId = :userId)")
    override fun findByIdAndUserId(@Param("id") id: Long, @Param("userId") userId: Long): Wallet?

    @Query("SELECT w FROM Wallet w INNER JOIN FETCH w.currency WHERE w.userId = :id")
    fun findByUserIdOrderByTypeAsc(@Param("id") id: Long, pageable: Pageable): List<Wallet>

}