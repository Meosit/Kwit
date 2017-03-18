package by.mksn.kwitapi.service

import by.mksn.kwitapi.entity.User

interface UserService {

    fun findByEmail(username: String): User?

    fun findById(id: Long): User?

}
