package by.mksn.kwit.controller.exception

import by.mksn.kwit.support.RestError
import by.mksn.kwit.support.RestErrorMessage
import org.springframework.boot.autoconfigure.web.ErrorController
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletRequest

private const val ERROR_PATH = "/error"
private const val ERROR_HTML_PATH = "/error/404.html"

@Controller
@RequestMapping(ERROR_PATH)
class WhiteLabelErrorPageController : ErrorController {

    @GetMapping("")
    fun error(request: HttpServletRequest): Any {
        if (request.getHeader(HttpHeaders.ACCEPT) != null && request.getHeader(HttpHeaders.ACCEPT).contains(MediaType.APPLICATION_JSON_VALUE)) {
            return ResponseEntity<Any>(
                    RestError(HttpStatus.NOT_FOUND,
                            RestErrorMessage("Error", "Resource not found")),
                    HttpStatus.NOT_FOUND)
        } else {
            return ERROR_HTML_PATH
        }
    }

    override fun getErrorPath() = ERROR_PATH

}