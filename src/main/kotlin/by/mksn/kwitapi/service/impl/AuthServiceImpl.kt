package by.mksn.kwitapi.service.impl

import by.mksn.kwitapi.entity.User
import by.mksn.kwitapi.entity.support.PasswordChangeDetails
import by.mksn.kwitapi.entity.support.RegistrationDetails
import by.mksn.kwitapi.repository.UserRepository
import by.mksn.kwitapi.service.AuthService
import by.mksn.kwitapi.service.exception.ServiceBadRequestException
import by.mksn.kwitapi.service.exception.ServiceException
import by.mksn.kwitapi.support.wrapJPACall
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = arrayOf(ServiceException::class))
class AuthServiceImpl(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder
) : AuthService {

    companion object {
        val logger = LoggerFactory.getLogger(AuthServiceImpl::class.java)!!
    }

    override fun changePassword(changeDetails: PasswordChangeDetails) {
        TODO("not implemented")
    }

    override fun register(registrationDetails: RegistrationDetails) {
        val existingUser = wrapJPACall { userRepository.findByEmail(registrationDetails.email ?: "") }
        if (existingUser == null) {
            val user = User(
                    email = registrationDetails.email,
                    passwordHash = passwordEncoder.encode(registrationDetails.password),
                    salaryDay = registrationDetails.salaryDay
            )
            wrapJPACall { userRepository.save(user) }
                    ?: throw ServiceBadRequestException("Error" to "Cannot register user")
            logger.info("User with email '${registrationDetails.email} successfully registered.")
        } else {
            throw ServiceBadRequestException("Error" to "User with such email is already exists.")
        }
    }
}