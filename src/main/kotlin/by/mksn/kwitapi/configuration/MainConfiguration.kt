package by.mksn.kwitapi.configuration

import by.mksn.kwitapi.controller.TestController
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(ControllerConfiguration::class)
@EnableAutoConfiguration
open class MainConfiguration : SpringBootServletInitializer() {

    override fun configure(builder: SpringApplicationBuilder): SpringApplicationBuilder {
        return builder.sources(MainConfiguration::class.java)
    }

}

@Configuration
open class ControllerConfiguration {

    @Bean
    open fun testController() = TestController()

}
