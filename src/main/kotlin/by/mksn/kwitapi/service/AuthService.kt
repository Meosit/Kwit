package by.mksn.kwitapi.service

import by.mksn.kwitapi.entity.support.PasswordChangeDetails
import by.mksn.kwitapi.entity.support.RegistrationDetails

interface AuthService {

    fun register(registrationDetails: RegistrationDetails)


    fun changePassword(changeDetails: PasswordChangeDetails)

}