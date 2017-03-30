package by.mksn.kwitapi.service.impl

import by.mksn.kwitapi.controller.UserRegistrationInfo
import by.mksn.kwitapi.model.User
import by.mksn.kwitapi.model.UserRepository
import by.mksn.kwitapi.service.AbstractCrudService
import by.mksn.kwitapi.service.UserService
import by.mksn.kwitapi.service.tryCallJPA

class UserServiceImpl(val userRepository: UserRepository) : AbstractCrudService<User, Long>(userRepository), UserService {

    override fun register(registrationInfo: UserRegistrationInfo) {
        TODO("not implemented")
    }

    override fun findByEmail(username: String): User?
            = tryCallJPA { userRepository.findByEmail(username) }
}
