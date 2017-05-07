package by.mksn.kwitapi.controller.impl

import by.mksn.kwitapi.controller.AuthController
import by.mksn.kwitapi.entity.support.PasswordChangeDetails
import by.mksn.kwitapi.entity.support.RegistrationDetails
import by.mksn.kwitapi.service.AuthService
import by.mksn.kwitapi.support.wrapServiceCall
import org.slf4j.LoggerFactory
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid


class AuthControllerImpl(
        private val authService: AuthService,
        private val defaultTokenServices: DefaultTokenServices
) : AuthController {

    companion object {
        val logger = LoggerFactory.getLogger(AuthControllerImpl::class.java)!!
    }

    override fun register(@Valid @RequestBody registrationDetails: RegistrationDetails) =
            wrapServiceCall(logger) { authService.register(registrationDetails) }

    override fun logout(principal: OAuth2Authentication) {
        val accessToken = defaultTokenServices.getAccessToken(principal)
        defaultTokenServices.revokeToken(accessToken.value)
    }

    override fun changePassword(passwordChangeDetails: PasswordChangeDetails) {
        TODO("not implemented")
    }
}