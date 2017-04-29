package by.mksn.kwitapi.controller.exception

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ValidationExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
            ex: MethodArgumentNotValidException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest
    ): ResponseEntity<Any> {
        val apiError = ValidationErrorInfo(
                status = status.value(),
                error = status.reasonPhrase,
                errors = ex.bindingResult.fieldErrors.map {
                    FieldErrorInfo(
                            field = it.field,
                            message = it.defaultMessage
                    )
                }
        )
        return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request)
    }

    override fun handleHttpMessageNotReadable(
            ex: HttpMessageNotReadableException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest): ResponseEntity<Any> {
        val cause = ex.cause
        val apiError = when (cause) {
            is InvalidFormatException -> ValidationErrorInfo(
                    HttpStatus.BAD_REQUEST,
                    FieldErrorInfo("unknown", "Invalid value '${cause.value}' of type ${cause.targetType.simpleName}")
            )
            else -> ValidationErrorInfo(status)
        }
        return handleExceptionInternal(ex, apiError, headers, status, request)
    }

}

data class ValidationErrorInfo(
        val status: Int,
        val error: String,
        val errors: List<FieldErrorInfo>?
) {
    constructor(status: HttpStatus, vararg errors: FieldErrorInfo) : this(
            status.value(),
            status.reasonPhrase,
            errors.asList())

    constructor(status: HttpStatus) : this(status.value(), status.reasonPhrase, null)
}

data class FieldErrorInfo(
        val field: String,
        val message: String
)
