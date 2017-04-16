package by.mksn.kwitapi.controller.exception

import org.springframework.boot.autoconfigure.web.ErrorController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/error")
class WhiteLabelErrorController : ErrorController {

    @RequestMapping("")
    fun handleError(): Nothing = throw NotFoundException()

    override fun getErrorPath() = "/error"
}