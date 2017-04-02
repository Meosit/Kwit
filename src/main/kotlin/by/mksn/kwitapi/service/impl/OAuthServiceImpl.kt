package by.mksn.kwitapi.service.impl

import by.mksn.kwitapi.controller.PasswordChangeDetails
import by.mksn.kwitapi.controller.RegistrationDetails
import by.mksn.kwitapi.exception.UserAlreadyExistsException
import by.mksn.kwitapi.model.User
import by.mksn.kwitapi.model.UserRepository
import by.mksn.kwitapi.service.OAuthService
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import javax.transaction.Transactional

class OAuthServiceImpl(val userRepository: UserRepository, val passwordEncoder: PasswordEncoder) : OAuthService{

    companion object {
        val logger = LoggerFactory.getLogger(OAuthServiceImpl::class.java)!!
    }

    @Transactional
    override fun register(registrationDetails: RegistrationDetails) {
        if (userRepository.findByEmail(registrationDetails.email) != null) {
            logger.info("User with ${registrationDetails.email} already exists")
            throw UserAlreadyExistsException("User with ${registrationDetails.email} already exists")
        }
        val newUser = User(
                email = registrationDetails.email,
                passwordHash = passwordEncoder.encode(registrationDetails.password),
                salaryDay = registrationDetails.salaryDay
        )
        val savedUser = userRepository.save(newUser)
    }

    override fun changePassword(changeDetails: PasswordChangeDetails) {
        TODO("Hasn't implemented yet")
    }
}