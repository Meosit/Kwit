package by.mksn.kwitapi.configuration

import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes
import org.springframework.web.context.request.RequestAttributes

class ErrorAttributesWithoutExceptionName : DefaultErrorAttributes() {

    private val EXCEPTION_KEY = "exception"

    override fun getErrorAttributes(
            requestAttributes: RequestAttributes,
            includeStackTrace: Boolean): Map<String, Any> {

        val errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace)
        errorAttributes.remove(EXCEPTION_KEY)
        return errorAttributes
    }

}