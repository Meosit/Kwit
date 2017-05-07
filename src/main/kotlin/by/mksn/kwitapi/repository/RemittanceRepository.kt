package by.mksn.kwitapi.repository

import by.mksn.kwitapi.entity.Remittance
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
                WHERE r.userId = :id""")
    fun findByUserIdOrderByDateDescIdDesc(@Param("id") id: Long, pageable: Pageable): List<Remittance>

}
