package by.mksn.kwitapi.controller

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class TestController {

    @RequestMapping(value = "/{name}", method = arrayOf(RequestMethod.GET))
    fun hello(@PathVariable(value = "name") name: String) = "Hello, $name!"

    @RequestMapping(value = "/", method = arrayOf(RequestMethod.GET))
    fun hello() = "Hello, User!"


}