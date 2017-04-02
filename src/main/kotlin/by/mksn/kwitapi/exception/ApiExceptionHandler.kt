package by.mksn.kwitapi.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

data class ApiError(
        val status: Int,
        val error: String,
        val message: String,
        val fieldErrors: List<ApiFieldError>? = null
)

data class ApiFieldError(
        val field: String,
        val code: String,
        val message: String
)

@ControllerAdvice
class ApiExceptionHandler : ResponseEntityExceptionHandler() {

    companion object {
        val logger = LoggerFactory.getLogger(ApiExceptionHandler::class.java)!!
    }

    override fun handleMethodArgumentNotValid(
            ex: MethodArgumentNotValidException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest
    ): ResponseEntity<Any> {
        val apiError = ApiError(
                status = HttpStatus.BAD_REQUEST.value(),
                error = HttpStatus.BAD_REQUEST.reasonPhrase,
                message = ex.localizedMessage,
                fieldErrors = ex.bindingResult.fieldErrors.map {
                    ApiFieldError(
                            field = it.field,
                            code = it.code,
                            message = it.defaultMessage
                    )
                }
        )
        return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request)
    }

}