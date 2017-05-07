package by.mksn.kwit.repository

import by.mksn.kwit.entity.User
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : PagingAndSortingRepository<User, Long> {
    fun findByEmail(email: String): User?
}