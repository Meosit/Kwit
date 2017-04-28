package by.mksn.kwitapi.controller.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR, reason = "Something went wrong")
open class ControllerException(
        message: String? = null,
        cause: Throwable? = null
) : RuntimeException(message, cause)

@ResponseStatus(HttpStatus.NOT_FOUND, reason = "Entity not found")
class NotFoundException(
        message: String? = null,
        cause: Throwable? = null
) : ControllerException(message, cause)


class UserAlreadyExistsException(
        message: String? = null,
        cause: Throwable? = null
) : ControllerException(message, cause)

@ResponseStatus(HttpStatus.BAD_REQUEST, reason = "Wrong input")
class BadRequestException(
        message: String? = null,
        cause: Throwable? = null
) : ControllerException(message, cause)

@ResponseStatus(HttpStatus.FORBIDDEN, reason = "Cannot perform this operation")
class AccessDeniedException(
        message: String? = null,
        cause: Throwable? = null
) : ControllerException(message, cause)