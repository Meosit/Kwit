package by.mksn.kwitapi.configuration

import by.mksn.kwitapi.configuration.security.SecurityConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(PersistenceConfiguration::class,
        ServiceConfiguration::class,
        ControllerConfiguration::class,
        SecurityConfiguration::class)
@EnableAutoConfiguration(exclude = arrayOf(
        org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration::class,
        org.springframework.boot.autoconfigure.security.SecurityFilterAutoConfiguration::class,
        org.springframework.boot.autoconfigure.security.FallbackWebSecurityAutoConfiguration::class,
        org.springframework.boot.autoconfigure.security.oauth2.OAuth2AutoConfiguration::class))
open class MainConfiguration : SpringBootServletInitializer() {

    override fun configure(builder: SpringApplicationBuilder): SpringApplicationBuilder
            = builder.sources(MainConfiguration::class.java)

}