package by.mksn.kwit.service.exception

import by.mksn.kwit.support.RestErrorMessage
import org.springframework.http.HttpStatus

open class ServiceException(
        message: String? = null,
        cause: Throwable? = null
) : RuntimeException(message, cause)

open class ServiceRequestException(
        val status: HttpStatus,
        val errors: List<RestErrorMessage>
) : ServiceException()

class ServiceConstraintFailException(
        errors: List<RestErrorMessage>
) : ServiceRequestException(HttpStatus.BAD_REQUEST, errors) {
    constructor(vararg errors: Pair<String, String>) : this(
            errors.map { RestErrorMessage(it.first, it.second) }
    )
}

class ServiceBadRequestException(
        errors: List<RestErrorMessage>
) : ServiceRequestException(HttpStatus.BAD_REQUEST, errors) {
    constructor(vararg errors: Pair<String, String>) : this(
            errors.map { RestErrorMessage(it.first, it.second) }
    )
}

class ServiceNotFoundException(
        errors: List<RestErrorMessage>
) : ServiceRequestException(HttpStatus.NOT_FOUND, errors) {
    constructor(vararg errors: Pair<String, String>) : this(
            errors.map { RestErrorMessage(it.first, it.second) }
    )
}

