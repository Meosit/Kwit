package by.mksn.kwit.service

import by.mksn.kwit.entity.support.PasswordChangeDetails
import by.mksn.kwit.entity.support.RegistrationDetails
import by.mksn.kwit.entity.support.SalaryInfo
import org.springframework.security.oauth2.provider.OAuth2Authentication

interface UserService {

    fun register(registrationDetails: RegistrationDetails)

    fun logout(principal: OAuth2Authentication)

    fun changePassword(changeDetails: PasswordChangeDetails, userId: Long, principal: OAuth2Authentication)

    fun findSalaryInfo(userId: Long): SalaryInfo?

    fun setSalaryInfo(salaryInfo: SalaryInfo, userId: Long)

}