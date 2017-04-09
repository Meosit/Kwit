package by.mksn.kwitapi.service

import by.mksn.kwitapi.entity.PasswordChangeDetails
import by.mksn.kwitapi.entity.RegistrationDetails

interface AuthService {
    fun register(registrationDetails: RegistrationDetails)
    fun changePassword(changeDetails: PasswordChangeDetails)
}