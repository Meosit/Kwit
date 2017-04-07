package by.mksn.kwitapi.configuration

import by.mksn.kwitapi.controller.exception.ValidationExceptionHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Configuration
class ControllerConfiguration {

    @Bean
    fun responseEntityExceptionHandler() : ResponseEntityExceptionHandler
            = ValidationExceptionHandler()

}
