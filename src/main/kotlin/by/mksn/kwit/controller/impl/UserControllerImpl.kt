package by.mksn.kwit.controller.impl

import by.mksn.kwit.configuration.security.Auth
import by.mksn.kwit.configuration.security.UserDetails
import by.mksn.kwit.controller.UserController
import by.mksn.kwit.entity.support.PasswordChangeDetails
import by.mksn.kwit.entity.support.RegistrationDetails
import by.mksn.kwit.entity.support.SalaryInfo
import by.mksn.kwit.service.UserService
import by.mksn.kwit.support.wrapServiceCall
import org.slf4j.LoggerFactory
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid


class UserControllerImpl(
        private val userService: UserService
) : UserController {

    companion object {
        val logger = LoggerFactory.getLogger(UserControllerImpl::class.java)!!
    }

    override fun register(@Valid @RequestBody registrationDetails: RegistrationDetails) =
            wrapServiceCall(logger) { userService.register(registrationDetails) }

    override fun getSalaryInfo(@Auth auth: UserDetails): SalaryInfo =
            wrapServiceCall(logger) { userService.findSalaryInfo(auth.userId) }
                    ?: SalaryInfo(null, null)

    override fun setSalaryInfo(@Valid @RequestBody salaryInfo: SalaryInfo, @Auth auth: UserDetails) =
            wrapServiceCall(logger) { userService.setSalaryInfo(salaryInfo, auth.userId) }

    override fun logout(principal: OAuth2Authentication) {
        wrapServiceCall(logger) { userService.logout(principal) }
    }

    override fun changePassword(@Valid @RequestBody passwordChangeDetails: PasswordChangeDetails, @Auth auth: UserDetails, principal: OAuth2Authentication) =
            wrapServiceCall(logger) { userService.changePassword(passwordChangeDetails, auth.userId, principal) }
}