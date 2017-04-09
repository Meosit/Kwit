package by.mksn.kwitapi

import by.mksn.kwitapi.controller.exception.BadRequestException
import by.mksn.kwitapi.controller.exception.ControllerException
import by.mksn.kwitapi.service.exception.ConstraintFailServiceException
import by.mksn.kwitapi.service.exception.ServiceException
import org.slf4j.Logger
import org.springframework.dao.DataAccessException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException

fun <T> wrapJPACall(block: () -> T?): T? = try {
    block()
} catch (e: EmptyResultDataAccessException) {
    null
} catch (e: DataIntegrityViolationException) {
    throw ConstraintFailServiceException()
} catch (e: DataAccessException) {
    throw ServiceException()
}

fun <T> wrapServiceCall(logger: Logger, block: () -> T): T = try {
    block()
} catch (e: ConstraintFailServiceException) {
    throw BadRequestException(cause = e)
} catch (e: ServiceException) {
    logger.warn("Error while service call\n", e)
    throw ControllerException(cause = e)
}