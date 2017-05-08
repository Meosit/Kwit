package by.mksn.kwit.service

import by.mksn.kwit.entity.support.PasswordChangeDetails
import by.mksn.kwit.entity.support.RegistrationDetails
import by.mksn.kwit.entity.support.SalaryInfo

interface UserService {

    fun register(registrationDetails: RegistrationDetails)

    fun changePassword(changeDetails: PasswordChangeDetails)

    fun findSalaryInfo(userId: Long): SalaryInfo?

    fun setSalaryInfo(salaryInfo: SalaryInfo, userId: Long)

}