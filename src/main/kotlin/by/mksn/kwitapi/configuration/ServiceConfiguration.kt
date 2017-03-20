package by.mksn.kwitapi.configuration

import by.mksn.kwitapi.model.UserRepository
import by.mksn.kwitapi.service.UserService
import by.mksn.kwitapi.service.impl.UserServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ServiceConfiguration {

    @Bean
    fun userService(userRepository: UserRepository): UserService {
        return UserServiceImpl(userRepository)
    }

}