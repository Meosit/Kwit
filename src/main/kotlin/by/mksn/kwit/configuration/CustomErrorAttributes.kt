package by.mksn.kwit.configuration

import by.mksn.kwit.support.RestErrorMessage
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes
import org.springframework.web.context.request.RequestAttributes

class CustomErrorAttributes : DefaultErrorAttributes() {

    private val EXCEPTION_KEY = "exception"
    private val TIMESTAMP_KEY = "timestamp"
    private val MESSAGE_KEY = "message"
    private val PATH_KEY = "path"
    private val ERRORS_KEY = "errors"

    override fun getErrorAttributes(
            requestAttributes: RequestAttributes,
            includeStackTrace: Boolean): Map<String, Any> {

        val errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace)
        errorAttributes.remove(EXCEPTION_KEY)
        errorAttributes.remove(TIMESTAMP_KEY)
        errorAttributes.remove(PATH_KEY)
        val message = errorAttributes.remove(MESSAGE_KEY)
        errorAttributes.put(ERRORS_KEY, listOf(RestErrorMessage("Error", message.toString())))
        return errorAttributes
    }

}