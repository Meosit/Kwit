package by.mksn.kwitapi.service.impl

import by.mksn.kwitapi.controller.RegistrationDetails
import by.mksn.kwitapi.model.User
import by.mksn.kwitapi.model.UserRepository
import by.mksn.kwitapi.service.AbstractCrudService
import by.mksn.kwitapi.service.UserService
import by.mksn.kwitapi.service.tryCallJPA

class UserServiceImpl(val userRepository: UserRepository) : AbstractCrudService<User, Long>(userRepository), UserService {

    override fun register(registrationDetails: RegistrationDetails) {
        TODO("not implemented")
    }

    override fun findByEmail(username: String): User?
            = tryCallJPA { userRepository.findByEmail(username) }
}
