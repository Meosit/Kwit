package by.mksn.kwitapi.service

import by.mksn.kwitapi.model.User
import org.springframework.stereotype.Service

@Service
interface UserService {

    fun findByEmail(username: String): User?

    fun findById(id: Long): User?

}
