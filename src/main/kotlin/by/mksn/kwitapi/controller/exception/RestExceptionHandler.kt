package by.mksn.kwitapi.controller.exception

import by.mksn.kwitapi.support.RestError
import by.mksn.kwitapi.support.RestErrorMessage
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
                message = RestErrorMessage("Error", "Required request body is missing")
            }
            is InvalidFormatException -> {
                message = RestErrorMessage("Invalid entity", "Invalid value '${cause.value}' of type ${cause.targetType.simpleName}")
            }
            is JsonParseException -> {
                message = RestErrorMessage("Invalid JSON", "Invalid JSON format")
            }
            is JsonMappingException -> {
                val references = cause.path
                val messageBuilder = StringBuilder("Invalid values in fields")
                references.forEach { messageBuilder.append(" '").append(it.fieldName).append("'") }
                message = RestErrorMessage("Invalid JSON", messageBuilder.toString())
            }
            else -> {
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