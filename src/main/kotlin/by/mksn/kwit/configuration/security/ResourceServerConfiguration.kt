package by.mksn.kwit.configuration.security

import by.mksn.kwit.entity.support.UserRole
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore

@Configuration
@EnableResourceServer
class ResourceServerConfiguration(
        val tokenStore: TokenStore
) : ResourceServerConfigurerAdapter() {

    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.tokenStore(tokenStore)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .authorizeRequests()
                .antMatchers("/api/users/register").permitAll()
                .antMatchers(HttpMethod.GET, "/api/currencies/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/currencies/**").hasAnyAuthority(UserRole.ADMIN.name)
                .antMatchers(HttpMethod.POST, "/api/currencies/**").hasAnyAuthority(UserRole.ADMIN.name)
                .antMatchers(HttpMethod.PUT, "/api/currencies/**").hasAnyAuthority(UserRole.ADMIN.name)
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
    }

}