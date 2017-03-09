package by.mksn.kwitapi.configuration

import by.mksn.kwitapi.controller.TestController
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class ControllerConfiguration {

    @Bean
    open fun testController() = TestController()

}