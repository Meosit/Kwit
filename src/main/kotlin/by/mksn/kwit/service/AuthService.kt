package by.mksn.kwit.service

import by.mksn.kwit.entity.support.PasswordChangeDetails
import by.mksn.kwit.entity.support.RegistrationDetails

interface AuthService {

    fun register(registrationDetails: RegistrationDetails)


    fun changePassword(changeDetails: PasswordChangeDetails)

}