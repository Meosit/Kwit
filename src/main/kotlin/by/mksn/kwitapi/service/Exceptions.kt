package by.mksn.kwitapi.service

import org.springframework.dao.DataAccessException

fun <T> tryCallJPA(block: () -> T): T {
    try {
        return block()
    } catch (e: DataAccessException) {
        throw ServiceException(cause = e)
    }
}

class ServiceException(
        message: String? = null,
        cause: Throwable? = null
) : RuntimeException(message, cause)
