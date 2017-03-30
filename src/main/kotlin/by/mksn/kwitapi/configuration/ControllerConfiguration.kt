package by.mksn.kwitapi.configuration

import by.mksn.kwitapi.controller.impl.OAuthControllerImpl
import by.mksn.kwitapi.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ControllerConfiguration {

    @Bean
    fun userController(userService: UserService): OAuthControllerImpl
            = OAuthControllerImpl(userService)

}
