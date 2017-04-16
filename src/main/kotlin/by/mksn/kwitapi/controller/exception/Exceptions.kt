package by.mksn.kwitapi.controller.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
open class ControllerException(
        message: String? = null,
        cause: Throwable? = null
) : RuntimeException(message, cause)

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(
        message: String? = null,
        cause: Throwable? = null
) : ControllerException(message, cause)


class UserAlreadyExistsException(
        message: String? = null,
        cause: Throwable? = null
) : ControllerException(message, cause)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequestException(
        message: String? = null,
        cause: Throwable? = null
) : ControllerException(message, cause)

@ResponseStatus(HttpStatus.FORBIDDEN)
class AccessDeniedException(
        message: String? = null,
        cause: Throwable? = null
) : ControllerException(message, cause)