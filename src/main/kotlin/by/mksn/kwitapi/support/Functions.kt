package by.mksn.kwitapi.support

import by.mksn.kwitapi.controller.exception.ControllerException
import by.mksn.kwitapi.controller.exception.RequestException
import by.mksn.kwitapi.entity.support.IdAssignable
import by.mksn.kwitapi.service.exception.ServiceConstraintFailException
import by.mksn.kwitapi.service.exception.ServiceException
import by.mksn.kwitapi.service.exception.ServiceNotFoundException
import by.mksn.kwitapi.service.exception.ServiceRequestException
import org.slf4j.Logger
import org.springframework.dao.DataAccessException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import java.sql.Timestamp
import java.util.*

inline fun <T> wrapJPACall(block: () -> T): T = try {
    block()
} catch (e: DataIntegrityViolationException) {
    throw ServiceConstraintFailException("Invalid entity" to "Invalid entity references")
} catch (e: DataAccessException) {
    throw ServiceException(cause = e)
}

inline fun <T> wrapServiceCall(logger: Logger, block: () -> T): T = try {
    block()
} catch (e: ServiceRequestException) {
    logger.debug("Invalid user request")
    throw RequestException(e.status, e.errors)
} catch (e: ServiceException) {
    logger.warn("Error while service call\n", e)
    throw ControllerException(cause = e)
}

inline fun MutableList<RestErrorMessage>.isAny(block: MutableList<RestErrorMessage>.() -> Nothing) {
    if (this.size != 0) {
        block()
    }
}

fun MutableList<RestErrorMessage>.add(errorMessage: Pair<String, String>) {
    this.add(RestErrorMessage(errorMessage.first, errorMessage.second))
}

infix fun Timestamp.till(end: Timestamp) = TimestampRange(this, end)

infix fun Date.till(end: Date) = TimestampRange(
        Timestamp.from(this.toInstant()),
        Timestamp.from(end.toInstant())
)

fun notFoundException(title: String, message: String): Nothing =
        throw RequestException(HttpStatus.NOT_FOUND, title to message)

fun badRequestException(title: String, message: String): Nothing =
        throw RequestException(HttpStatus.BAD_REQUEST, title to message)

fun accessDeniedException(title: String, message: String): Nothing =
        throw RequestException(HttpStatus.FORBIDDEN, title to message)

inline fun Any?.ifNull(block: (Any?) -> Nothing): Nothing? {
    if (this == null) {
        block(this)
    }
    return null
}

fun <ID, T : IdAssignable<ID>> T?.ifNullServiceNotFound(id: ID? = null): T {
    if (this == null) {
        throw ServiceNotFoundException("Error" to "Entity ${if (id == null) "" else "with id $id "}not found")
    }
    return this
}

fun <ID, T : IdAssignable<ID>> T?.ifNullNotFound(id: ID? = null): T {
    if (this == null) {
        throw RequestException(HttpStatus.NOT_FOUND,
                "Error" to "Entity ${if (id == null) "" else "with id $id "}not found")
    }
    return this
}