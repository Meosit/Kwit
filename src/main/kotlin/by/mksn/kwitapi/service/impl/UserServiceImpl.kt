package by.mksn.kwitapi.service.impl

import by.mksn.kwitapi.model.User
import by.mksn.kwitapi.model.UserRepository
import by.mksn.kwitapi.service.UserService

class UserServiceImpl(val userRepository: UserRepository) : UserService {

    override fun findByEmail(username: String): User?
            = userRepository.findByEmail(username)

    override fun findById(id: Long): User?
            = userRepository.findOne(id)
}
