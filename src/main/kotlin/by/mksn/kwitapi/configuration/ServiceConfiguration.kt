package by.mksn.kwitapi.configuration

import by.mksn.kwitapi.model.UserRepository
import by.mksn.kwitapi.service.OAuthService
import by.mksn.kwitapi.service.UserService
import by.mksn.kwitapi.service.impl.OAuthServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder


@Configuration
class ServiceConfiguration {

    @Bean
    fun oAuthService(userRepository: UserRepository, passwordEncoder: PasswordEncoder): OAuthService
            = OAuthServiceImpl(userRepository, passwordEncoder)
}