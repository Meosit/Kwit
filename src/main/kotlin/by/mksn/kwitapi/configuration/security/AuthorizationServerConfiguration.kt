package by.mksn.kwitapi.configuration.security

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.security.oauth2.provider.token.TokenStore
import javax.sql.DataSource

@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfiguration(
        val dataSource: DataSource,
        val passwordEncoder: PasswordEncoder,
        @Qualifier("authenticationManagerBean")
        val authenticationManager: AuthenticationManager,
        val userDetailsService: UserDetailsService,
        val tokenEnhancer: TokenEnhancer,
        val tokenStore: TokenStore
) : AuthorizationServerConfigurerAdapter() {


    @Bean
    protected fun authorizationCodeServices(): AuthorizationCodeServices
            = JdbcAuthorizationCodeServices(dataSource)

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints
                .authorizationCodeServices(authorizationCodeServices())
                .tokenStore(tokenStore)
                .authenticationManager(this.authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenEnhancer(tokenEnhancer)
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder)
    }

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security.allowFormAuthenticationForClients()
    }

    @Bean
    @Primary
    fun tokenServices(): DefaultTokenServices {
        val tokenServices = DefaultTokenServices()
        tokenServices.setSupportRefreshToken(true)
        tokenServices.setTokenStore(tokenStore)
        tokenServices.setTokenEnhancer(tokenEnhancer)
        return tokenServices
    }

}