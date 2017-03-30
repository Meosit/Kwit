package by.mksn.kwitapi.controller.impl

import by.mksn.kwitapi.controller.AbstractCrudController
import by.mksn.kwitapi.controller.UserController
import by.mksn.kwitapi.controller.UserRegistrationInfo
import by.mksn.kwitapi.model.User
import by.mksn.kwitapi.service.UserService
import org.slf4j.LoggerFactory


class UserControllerImpl(
        userService: UserService
) : AbstractCrudController<User, Long>(userService, logger), UserController {

    override fun register(userRegistrationInfo: UserRegistrationInfo) {
        TODO("not implemented")
    }

    companion object {
        val logger = LoggerFactory.getLogger(UserControllerImpl::class.java)!!
    }

}