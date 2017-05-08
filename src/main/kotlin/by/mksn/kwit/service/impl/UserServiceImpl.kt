package by.mksn.kwit.service.impl

import by.mksn.kwit.entity.User
import by.mksn.kwit.entity.support.PasswordChangeDetails
import by.mksn.kwit.entity.support.RegistrationDetails
import by.mksn.kwit.entity.support.SalaryInfo
import by.mksn.kwit.repository.CurrencyRepository
import by.mksn.kwit.repository.UserRepository
import by.mksn.kwit.service.UserService
import by.mksn.kwit.service.exception.ServiceBadRequestException
import by.mksn.kwit.service.exception.ServiceException
import by.mksn.kwit.support.wrapJPACall
import by.mksn.kwit.support.wrapJPAModifyingCall
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = arrayOf(ServiceException::class))
class UserServiceImpl(
        private val userRepository: UserRepository,
        private val currencyRepository: CurrencyRepository,
        private val passwordEncoder: PasswordEncoder,
        private val defaultTokenServices: DefaultTokenServices
) : UserService {
    companion object {

        val logger = LoggerFactory.getLogger(UserServiceImpl::class.java)!!

    }

    override fun changePassword(changeDetails: PasswordChangeDetails, userId: Long, principal: OAuth2Authentication) {
        val user = wrapJPACall { userRepository.findOne(userId) }
        if (user != null) {
            if (passwordEncoder.matches(changeDetails.password, user.passwordHash)) {
                user.passwordHash = passwordEncoder.encode(changeDetails.newPassword)
                wrapJPAModifyingCall { userRepository.save(user) }
                        ?: throw ServiceBadRequestException("Error" to "Cannot update password")
                logout(principal)
                logger.debug("Password changed for user[${user.id}][${user.email}]")
            } else {
                throw ServiceBadRequestException("Error" to "Wrong password")
            }
        } else {
            logger.warn("Invalid user id '$userId' passed!")
            throw ServiceBadRequestException("Error" to "User doesn't exist.")
        }
    }

    override fun logout(principal: OAuth2Authentication) {
        val accessToken = defaultTokenServices.getAccessToken(principal)
        defaultTokenServices.revokeToken(accessToken.value)
    }

    override fun register(registrationDetails: RegistrationDetails) {
        val existingUser = wrapJPACall { userRepository.findByEmail(registrationDetails.email ?: "") }
        if (existingUser == null) {
            val user = User(
                    email = registrationDetails.email,
                    passwordHash = passwordEncoder.encode(registrationDetails.password)
            )
            wrapJPAModifyingCall { userRepository.save(user) }
                    ?: throw ServiceBadRequestException("Error" to "Cannot register user")
            logger.info("User with email '${registrationDetails.email} successfully registered.")
        } else {
            logger.debug("User with email '${registrationDetails.email} already exists, registration failed.")
            throw ServiceBadRequestException("Error" to "User with such email is already exists.")
        }
    }

    override fun findSalaryInfo(userId: Long): SalaryInfo? {
        val user = wrapJPACall { userRepository.findOne(userId) }
        if (user != null) {
            return if (user.salaryDay == null || user.salaryCurrency == null)
                null
            else
                SalaryInfo(user.salaryDay, user.salaryCurrency?.code)
        } else {
            logger.warn("Invalid user id '$userId' passed!")
            throw ServiceBadRequestException("Error" to "User doesn't exist.")
        }
    }

    override fun setSalaryInfo(salaryInfo: SalaryInfo, userId: Long) {
        val currency = wrapJPACall { currencyRepository.findByCode(salaryInfo.salaryCurrencyCode ?: "") }
        if (currency != null) {
            val user = wrapJPACall { userRepository.findOne(userId) }
            if (user != null) {
                user.salaryDay = salaryInfo.salaryDay
                user.salaryCurrency = currency
                wrapJPAModifyingCall { userRepository.save(user) }
                        ?: throw ServiceBadRequestException("Error" to "Cannot set salary info")
            } else {
                logger.warn("Invalid user id '$userId' passed!")
                throw ServiceBadRequestException("Error" to "User doesn't exist.")
            }
        } else {
            logger.debug("Failed setting salary info: invalid currency code '${salaryInfo.salaryCurrencyCode}'")
            throw ServiceBadRequestException("Error" to
                    "Currency with code '${salaryInfo.salaryCurrencyCode}' doesn't exist.")
        }
    }
}