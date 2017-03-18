package by.mksn.kwitapi.repository

import by.mksn.kwitapi.entity.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User?, Long> {

    fun findByEmail(email: String): User?

}
