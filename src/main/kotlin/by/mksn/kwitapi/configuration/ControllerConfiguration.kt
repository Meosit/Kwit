package by.mksn.kwitapi.configuration

import by.mksn.kwitapi.controller.OAuthController
import by.mksn.kwitapi.exception.ApiExceptionHandler
import by.mksn.kwitapi.controller.impl.OAuthControllerImpl
import by.mksn.kwitapi.model.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Configuration
class ControllerConfiguration {

    @Bean
    fun responseEntityExceptionHandler() : ResponseEntityExceptionHandler
            = ApiExceptionHandler()

    @Bean
    fun oAuthController(tokenStore: TokenStore, userRepository: UserRepository): OAuthController
            = OAuthControllerImpl(tokenStore, userRepository)

}
