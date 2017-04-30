package by.mksn.kwitapi.controller.exception

import by.mksn.kwitapi.support.RestError
import by.mksn.kwitapi.support.RestErrorMessage
import org.springframework.beans.ConversionNotSupportedException
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.converter.HttpMessageNotWritableException
import org.springframework.validation.BindException
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingPathVariableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.context.request.WebRequest
import org.springframework.web.context.request.async.AsyncRequestTimeoutException
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

open class BaseRestExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionDefault(ex, headers, status, request)
    }

    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionDefault(ex, headers, status, request)
    }

    override fun handleNoHandlerFoundException(ex: NoHandlerFoundException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionDefault(ex, headers, status, request)
    }

    override fun handleTypeMismatch(ex: TypeMismatchException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionDefault(ex, headers, status, request)
    }

    override fun handleHttpRequestMethodNotSupported(ex: HttpRequestMethodNotSupportedException, headers: HttpHeaders, status: HttpStatus, request: WebRequest)
            = handleExceptionDefault(ex, headers, status, request)

    override fun handleMissingServletRequestParameter(ex: MissingServletRequestParameterException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionDefault(ex, headers, status, request)
    }

    override fun handleMissingPathVariable(ex: MissingPathVariableException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionDefault(ex, headers, status, request)
    }

    override fun handleServletRequestBindingException(ex: ServletRequestBindingException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionDefault(ex, headers, status, request)
    }

    override fun handleHttpMediaTypeNotSupported(ex: HttpMediaTypeNotSupportedException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionDefault(ex, headers, status, request)
    }

    override fun handleHttpMediaTypeNotAcceptable(ex: HttpMediaTypeNotAcceptableException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionDefault(ex, headers, status, request)
    }

    override fun handleBindException(ex: BindException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionDefault(ex, headers, status, request)
    }

    override fun handleMissingServletRequestPart(ex: MissingServletRequestPartException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionDefault(ex, headers, status, request)
    }

    override fun handleAsyncRequestTimeoutException(ex: AsyncRequestTimeoutException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionDefault(ex, headers, status, request)
    }

    override fun handleConversionNotSupported(ex: ConversionNotSupportedException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionDefault(ex, headers, status, request)
    }

    override fun handleHttpMessageNotWritable(ex: HttpMessageNotWritableException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionDefault(ex, headers, status, request)
    }

    protected fun handleExceptionDefault(ex: Exception,
                                         headers: HttpHeaders,
                                         status: HttpStatus,
                                         request: WebRequest
    ): ResponseEntity<Any> {
        val message = ex.message
        val apiError = if (message != null)
            RestError(status, RestErrorMessage("Error", message))
        else
            RestError(status)
        return handleExceptionInternal(ex, apiError, headers, status, request)
    }


}