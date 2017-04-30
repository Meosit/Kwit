package by.mksn.kwitapi

import by.mksn.kwitapi.controller.exception.ControllerException
import by.mksn.kwitapi.controller.exception.RestErrorMessage
import by.mksn.kwitapi.service.exception.ServiceConstraintFailException
import by.mksn.kwitapi.service.exception.ServiceException
import by.mksn.kwitapi.service.exception.ServiceRequestException
import org.slf4j.Logger
import org.springframework.dao.DataAccessException
import org.springframework.dao.DataIntegrityViolationException

inline fun <T> wrapJPACall(block: () -> T): T = try {
    block()
} catch (e: DataIntegrityViolationException) {
    throw ServiceConstraintFailException("Entity" to "Cannot  entity")
} catch (e: DataAccessException) {
    throw ServiceException(cause = e)
}

inline fun <T> wrapServiceCall(logger: Logger, block: () -> T): T = try {
    block()
} catch (e: ServiceRequestException) {
    logger.debug("Invalid user request")
    throw e
} catch (e: ServiceException) {
    logger.warn("Error while service call\n", e)
    throw ControllerException(cause = e)
}

inline fun MutableList<RestErrorMessage>.isAny(block: (MutableList<RestErrorMessage>) -> Nothing) {
    if (this.size != 0) {
        block(this)
    }
}

fun MutableList<RestErrorMessage>.add(errorMessage: Pair<String, String>) {
    this.add(RestErrorMessage(errorMessage.first, errorMessage.second))
}