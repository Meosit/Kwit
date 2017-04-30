package by.mksn.kwitapi.controller.exception

import by.mksn.kwitapi.support.RestErrorMessage
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR, reason = "Something went wrong")
open class ControllerException(
        message: String? = null,
        cause: Throwable? = null
) : RuntimeException(message, cause)

class RequestException(
        val status: HttpStatus,
        val errors: List<RestErrorMessage>
) : RuntimeException() {
    constructor(status: HttpStatus, vararg errors: Pair<String, String>) : this(
            status,
            errors.map { RestErrorMessage(it.first, it.second) }
    )
}