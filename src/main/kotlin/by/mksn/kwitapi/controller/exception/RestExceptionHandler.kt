package by.mksn.kwitapi.controller.exception

import by.mksn.kwitapi.support.RestError
import by.mksn.kwitapi.support.RestErrorMessage
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.io.JsonEOFException
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
import org.springframework.web.servlet.NoHandlerFoundException

@ControllerAdvice
class RestExceptionHandler : BaseRestExceptionHandler() {

    override fun handleMethodArgumentNotValid(
            ex: MethodArgumentNotValidException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest
    ): ResponseEntity<Any> {
        val apiError = RestError(status,
                ex.bindingResult.fieldErrors.map { RestErrorMessage("Field '${it.field}'", it.defaultMessage) })
        return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request)
    }

    override fun handleNoHandlerFoundException(
            ex: NoHandlerFoundException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest
    ): ResponseEntity<Any> {
        val apiError = RestError(status, RestErrorMessage(
                title = "Invalid path",
                message = "'${ex.requestURL}'"
        ))
        return handleExceptionInternal(ex, apiError, headers, status, request)
    }

    override fun handleHttpMessageNotReadable(
            ex: HttpMessageNotReadableException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest): ResponseEntity<Any> {
        val cause = ex.cause
        val newStatus: HttpStatus
        val message: RestErrorMessage
        when (cause) {
            is InvalidFormatException -> {
                newStatus = HttpStatus.BAD_REQUEST
                message = RestErrorMessage("Invalid entity", "Invalid value '${cause.value}' of type ${cause.targetType.simpleName}")
            }
            is JsonParseException -> {
                newStatus = HttpStatus.BAD_REQUEST
                message = RestErrorMessage("Invalid JSON", "Invalid JSON format")
            }
            is JsonEOFException -> {
                newStatus = HttpStatus.BAD_REQUEST
                message = RestErrorMessage("Invalid JSON", "Invalid JSON format")
            }
            else -> {
                newStatus = status
                message = RestErrorMessage("Message not readable", cause?.message ?: "Unknown error")
            }
        }
        val apiError = RestError(status, message)
        return handleExceptionInternal(ex, apiError, headers, newStatus, request)
    }

    override fun handleTypeMismatch(
            ex: TypeMismatchException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest): ResponseEntity<Any> {
        val apiError = RestError(status, RestErrorMessage(
                title = "Invalid path variable",
                message = "Invalid value '${ex.value}' for type '${ex.requiredType?.simpleName}'"
        ))
        return handleExceptionInternal(ex, apiError, headers, status, request)
    }

    @ExceptionHandler(RequestException::class)
    fun handleServiceBadRequest(ex: RequestException): ResponseEntity<Any> {
        val apiError = RestError(ex.status, ex.errors)
        return ResponseEntity(apiError, ex.status)
    }
}