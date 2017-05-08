package by.mksn.kwit.support

import by.mksn.kwit.controller.exception.ControllerException
import by.mksn.kwit.controller.exception.RequestException
import by.mksn.kwit.entity.support.BaseEntity
import by.mksn.kwit.service.exception.*
import org.slf4j.Logger
import org.springframework.dao.DataAccessException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
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

inline fun <T> wrapJPAModifyingCall(block: () -> T): T? = try {
    block()
} catch (e: EmptyResultDataAccessException) {
    null
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

fun notFoundException(title: String, message: String): Nothing =
        throw RequestException(HttpStatus.NOT_FOUND, title to message)

fun badRequestException(title: String, message: String): Nothing =
        throw RequestException(HttpStatus.BAD_REQUEST, title to message)

fun MutableList<RestErrorMessage>.throwIfNeed(): Nothing? =
        if (this.isNotEmpty()) throw ServiceBadRequestException(this) else null

fun MutableList<RestErrorMessage>.add(title: String, message: String) {
    this.add(RestErrorMessage(title, message))
}

inline fun <T> T?.ifNull(block: T.() -> T): T {
    return if (this == null) block() else this
}

fun <ID, T : BaseEntity<ID>> T?.ifNullServiceNotFound(id: ID? = null): T {
    if (this == null) {
        throw ServiceNotFoundException("Error" to "Entity ${if (id == null) "" else "with id '$id' "}not found")
    }
    return this
}

fun <ID, T : BaseEntity<ID>> T?.ifNullNotFound(id: ID? = null): T {
    if (this == null) {
        throw RequestException(HttpStatus.NOT_FOUND,
                "Error" to "Entity ${if (id == null) "" else "with id '$id' "}not found")
    }
    return this
}

infix fun Date.till(end: Date) = DateRange(this, end)

fun Date.toStamp(): Timestamp = Timestamp.from(this.toInstant())

