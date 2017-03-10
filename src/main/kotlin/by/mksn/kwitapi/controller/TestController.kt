package by.mksn.kwitapi.controller

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class TestController {

    @RequestMapping(value = "/{name}",
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun hello(@PathVariable("name") name: String)
            = ResponseEntity<Greeting>(Greeting("Hello, $name!"), HttpStatus.OK)


    @RequestMapping(value = "/",
            method = arrayOf(RequestMethod.GET),
            produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun hello() = ResponseEntity<Greeting>(Greeting("Hello, User!"), HttpStatus.OK)


}

data class Greeting(var greeting: String)