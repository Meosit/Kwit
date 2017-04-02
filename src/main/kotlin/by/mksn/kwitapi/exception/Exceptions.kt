package by.mksn.kwitapi.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR, reason = "Cannot perform that operation")
class ControllerException(
        message: String? = null,
        cause: Throwable? = null
) : RuntimeException(message, cause)

@ResponseStatus(HttpStatus.NOT_FOUND, reason = "User with that id not found")
class UserNotFoundException(
        message: String? = null,
        cause: Throwable? = null
) : RuntimeException(message, cause)


class UserAlreadyExistsException(
        message: String? = null,
        cause: Throwable? = null
) : RuntimeException(message, cause)