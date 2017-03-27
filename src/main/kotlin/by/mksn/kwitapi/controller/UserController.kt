package by.mksn.kwitapi.controller

import by.mksn.kwitapi.configuration.security.JdbcUserDetailsService
import by.mksn.kwitapi.controller.exception.UserNotFoundException
import by.mksn.kwitapi.model.User
import by.mksn.kwitapi.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
        val userService: UserService
) {

    companion object {
        val logger = LoggerFactory.getLogger(UserController::class.java)!!
    }

    @GetMapping("/{id}")
    @Throws(UserNotFoundException::class)
    fun getById(@PathVariable("id") id: Long): User {
        val user = userService.findById(id)
        if (user == null) {
            logger.debug("Cannot found user with id: $id")
            throw UserNotFoundException()
        }
        return user
    }

    @GetMapping("/me")
    @Throws(UserNotFoundException::class)
    fun getMe(@AuthenticationPrincipal userDetails: JdbcUserDetailsService.UserDetails): User {
        val user = userService.findById(userDetails.getUserId())
        if (user == null) {
            logger.debug("Cannot found user with id: ${userDetails.getUserId()}")
            throw UserNotFoundException()
        }
        return user
    }

}