package by.mksn.kwit.repository

import by.mksn.kwit.entity.Remittance
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface RemittanceRepository :
        PagingAndSortingRepository<Remittance, Long>,
        PersonalCrudRepository<Remittance, Long> {

    @Query("""SELECT r FROM Remittance r
                INNER JOIN FETCH r.walletDonor
                INNER JOIN FETCH r.walletAcceptor
                WHERE (r.id = :id) AND (r.userId = :userId)""")
    override fun findByIdAndUserId(@Param("id") id: Long, @Param("userId") userId: Long): Remittance?

    @Query("""SELECT r FROM Remittance r
                INNER JOIN FETCH r.walletDonor
                INNER JOIN FETCH r.walletAcceptor
                WHERE r.userId = :id
                ORDER BY r.date DESC""",
            countQuery = "SELECT COUNT(r) FROM Remittance r WHERE r.userId = :id")
    fun findByUserId(@Param("id") id: Long, pageable: Pageable): Page<Remittance>

}
