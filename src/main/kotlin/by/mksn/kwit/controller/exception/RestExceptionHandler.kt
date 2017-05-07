package by.mksn.kwit.controller.exception

import by.mksn.kwit.support.OauthError
import by.mksn.kwit.support.RestError
import by.mksn.kwit.support.RestErrorMessage
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class RestExceptionHandler : BaseRestExceptionHandler() {

    override fun handleMethodArgumentNotValid(
            ex: MethodArgumentNotValidException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest
    ): ResponseEntity<Any> {
        logger.debug("Invalid arguments passed: ${ex.bindingResult.fieldErrors}")
        val apiError = RestError(status,
                ex.bindingResult.fieldErrors.map { RestErrorMessage("Field '${it.field}'", it.defaultMessage) })
        return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request)
    }

    override fun handleHttpMessageNotReadable(
            ex: HttpMessageNotReadableException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest): ResponseEntity<Any> {
        val cause = ex.cause
        val message: RestErrorMessage
        when (cause) {
            null -> {
                logger.debug("Request body is missing", ex)
                message = RestErrorMessage("Error", "Required request body is missing")
            }
            is InvalidFormatException -> {
                logger.debug("Invalid value in JSON: ${cause.message}")
                message = RestErrorMessage("Invalid entity", "Invalid value '${cause.value}' of type ${cause.targetType.simpleName}")
            }
            is JsonParseException -> {
                logger.debug("Invalid JSON, cannot deserialize: ${cause.message}")
                message = RestErrorMessage("Invalid request", "Cannot read request")
            }
            is JsonMappingException -> {
                logger.debug("Invalid values in JSON: ${cause.message}")
                val references = cause.path
                val messageBuilder = StringBuilder("Invalid values in fields")
                references.forEach { messageBuilder.append(" '").append(it.fieldName).append("'") }
                message = RestErrorMessage("Invalid request", messageBuilder.toString())
            }
            else -> {
                logger.debug("Message not readable: ${cause.message}")
                message = RestErrorMessage("Message not readable", cause.message ?: "Unknown error")
            }
        }
        val apiError = RestError(status, message)
        return handleExceptionInternal(ex, apiError, headers, status, request)
    }

    override fun handleTypeMismatch(
            ex: TypeMismatchException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest): ResponseEntity<Any> {
        logger.debug("Invalid path variable: Invalid value '${ex.value}' for type '${ex.requiredType?.simpleName}'")
        val apiError = RestError(status, RestErrorMessage(
                title = "Invalid path variable",
                message = "Invalid value '${ex.value}' for type '${ex.requiredType?.simpleName}'"
        ))
        return handleExceptionInternal(ex, apiError, headers, status, request)
    }

    @ExceptionHandler(RequestException::class)
    fun handleBadRequest(ex: RequestException): ResponseEntity<Any> {
        logger.debug("Bad request from business logic: ${ex.status} ${ex.errors}")
        val apiError = RestError(ex.status, ex.errors)
        return ResponseEntity(apiError, ex.status)
    }


    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(ex: AccessDeniedException): ResponseEntity<Any> {
        logger.debug("Access denied from business logic")
        return ResponseEntity(OauthError("access_denied", "Access is denied"), HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(ControllerException::class)
    fun handleInternalError(ex: ControllerException): ResponseEntity<Any> {
        logger.error("Internal server error", ex)
        val apiError = RestError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                RestErrorMessage("Error", "Internal Server Error"))
        return ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(Exception::class)
    fun handleInternalErrorExplicit(ex: Exception): ResponseEntity<Any> {
        logger.error("Internal server error", ex)
        val apiError = RestError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                RestErrorMessage("Error", "Internal Server Error"))
        return ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}