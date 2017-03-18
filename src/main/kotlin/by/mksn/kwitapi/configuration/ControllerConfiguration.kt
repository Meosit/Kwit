package by.mksn.kwitapi.configuration

import by.mksn.kwitapi.controller.UserController
import by.mksn.kwitapi.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class ControllerConfiguration {

    @Bean
    open fun userController(userService: UserService)
            = UserController(userService)

}
