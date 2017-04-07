package by.mksn.kwitapi.service

import by.mksn.kwitapi.controller.util.PasswordChangeDetails
import by.mksn.kwitapi.controller.util.RegistrationDetails

interface AuthService {
    fun register(registrationDetails: RegistrationDetails)
    fun changePassword(changeDetails: PasswordChangeDetails)
}