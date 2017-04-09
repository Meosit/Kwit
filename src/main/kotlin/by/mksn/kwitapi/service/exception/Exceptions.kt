package by.mksn.kwitapi.service.exception

open class ServiceException(
        message: String? = null,
        cause: Throwable? = null
) : RuntimeException(message, cause)

class ConstraintFailServiceException(
        message: String? = null,
        cause: Throwable? = null
) : ServiceException(message, cause)