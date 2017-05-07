package by.mksn.kwit.controller.exception

import by.mksn.kwit.support.RestErrorMessage
import org.springframework.http.HttpStatus

open class ControllerException(
        message: String? = null,
        cause: Throwable? = null
) : RuntimeException(message, cause)

class RequestException(
        val status: HttpStatus,
        val errors: List<RestErrorMessage>
) : ControllerException() {
    constructor(status: HttpStatus, vararg errors: Pair<String, String>) : this(
            status,
            errors.map { RestErrorMessage(it.first, it.second) }
    )
}

class AccessDeniedException(
        message: String? = null,
        cause: Throwable? = null
) : ControllerException(message, cause)