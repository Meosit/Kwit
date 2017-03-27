package by.mksn.kwitapi.model

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
    fun findByCode(code: String): Currency?
}

@Repository
interface WalletRepository : CrudRepository<Wallet, Long> {
    fun findByUserId(id: Long): List<Wallet>
}

@Repository
interface CategoryRepository : CrudRepository<Category, Long> {
    fun findByUserId(id: Long): List<Category>
}

interface RemittanceRepository : PagingAndSortingRepository<Remittance, Long> {
    fun findByUserId(id: Long, pageable: Pageable): List<Remittance>
}