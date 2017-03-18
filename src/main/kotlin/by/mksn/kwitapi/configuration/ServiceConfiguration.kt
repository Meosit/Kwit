package by.mksn.kwitapi.configuration

import by.mksn.kwitapi.repository.UserRepository
import by.mksn.kwitapi.service.UserService
import by.mksn.kwitapi.service.impl.UserServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
open class ServiceConfiguration {

    @Bean
    open fun userService(userRepository: UserRepository): UserService {
        return UserServiceImpl(userRepository)
    }

}