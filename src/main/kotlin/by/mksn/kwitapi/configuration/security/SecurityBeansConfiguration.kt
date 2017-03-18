package by.mksn.kwitapi.configuration.security

import by.mksn.kwitapi.DEFAULT_BCRYPT_STRENGTH
import by.mksn.kwitapi.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import javax.sql.DataSource


@Configuration
open class SecurityBeansConfiguration {

    @Bean
    open fun tokenEnhancer(): TokenEnhancer
            = CustomTokenEnhancer()

    @Bean
    open fun passwordEncoder(): PasswordEncoder
            = BCryptPasswordEncoder(DEFAULT_BCRYPT_STRENGTH)

    @Bean
    open fun userDetailsService(userService: UserService): UserDetailsService
            = JdbcUserDetailsService(userService)

    @Bean
    open fun tokenStore(dataSource: DataSource): TokenStore {
        return JdbcTokenStore(dataSource)
    }
}