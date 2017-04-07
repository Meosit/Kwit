package by.mksn.kwitapi.controller.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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

}

data class ValidationErrorInfo(
        val status: Int,
        val error: String,
        val errors: List<FieldErrorInfo>
)

data class FieldErrorInfo(
        val field: String,
        val message: String
)
