package by.mksn.kwitapi.configuration

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(ControllerConfiguration::class,
        ResourceServerConfiguration::class,
        AuthorizationServerConfiguration::class,
        OAuth2SecurityConfiguration::class,
        MethodSecurityConfiguration::class
)
@EnableAutoConfiguration
open class MainConfiguration : SpringBootServletInitializer() {

    override fun configure(builder: SpringApplicationBuilder): SpringApplicationBuilder {
        return builder.sources(MainConfiguration::class.java)
    }

}

