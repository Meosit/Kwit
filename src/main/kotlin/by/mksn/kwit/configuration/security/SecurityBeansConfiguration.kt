package by.mksn.kwit.configuration.security

import by.mksn.kwit.repository.UserRepository
import by.mksn.kwit.support.DEFAULT_BCRYPT_STRENGTH
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
class SecurityBeansConfiguration {

    @Bean
    fun tokenEnhancer(): TokenEnhancer
            = CustomTokenEnhancer()

    @Bean
    fun passwordEncoder(): PasswordEncoder
            = BCryptPasswordEncoder(DEFAULT_BCRYPT_STRENGTH)

    @Bean
    fun userDetailsService(userRepository: UserRepository): UserDetailsService
            = JdbcUserDetailsService(userRepository)

    @Bean
    fun tokenStore(dataSource: DataSource): TokenStore
            = JdbcTokenStore(dataSource)

}