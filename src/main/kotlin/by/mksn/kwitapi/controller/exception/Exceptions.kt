package by.mksn.kwitapi.controller.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal server error")
open class ControllerException(
        message: String? = null,
        cause: Throwable? = null
) : RuntimeException(message, cause)

@ResponseStatus(HttpStatus.NOT_FOUND, reason = "Not found")
class NotFoundException(
        message: String? = null,
        cause: Throwable? = null
) : ControllerException(message, cause)


class UserAlreadyExistsException(
        message: String? = null,
        cause: Throwable? = null
) : ControllerException(message, cause)

@ResponseStatus(HttpStatus.BAD_REQUEST, reason = "Bad request")
class BadRequestException(
        message: String? = null,
        cause: Throwable? = null
) : ControllerException(message, cause)