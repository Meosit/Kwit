package by.mksn.kwitapi.controller.impl

import by.mksn.kwitapi.controller.OAuthController
import by.mksn.kwitapi.controller.PasswordChangeDetails
import by.mksn.kwitapi.controller.RegistrationDetails
import by.mksn.kwitapi.model.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


class OAuthControllerImpl(val tokenStore: TokenStore, val userRepository: UserRepository) : OAuthController {

    companion object {
        val logger = LoggerFactory.getLogger(OAuthControllerImpl::class.java)!!
    }

    override fun changePassword(@Valid @RequestBody passwordChangeDetails: PasswordChangeDetails) {

    }

    override fun register(@Valid @RequestBody registrationDetails: RegistrationDetails) {

    }

    @ResponseStatus(HttpStatus.OK)
    override fun logout(request: HttpServletRequest) {
        val authHeader = request.getHeader("Authorization")
        if (authHeader != null) {
            val tokenValue = authHeader.replace("Bearer", "").trim { it <= ' ' }
            val accessToken = tokenStore.readAccessToken(tokenValue)
            tokenStore.removeAccessToken(accessToken)
        }
    }

}