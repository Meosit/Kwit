package by.mksn.kwitapi.controller

import org.springframework.web.bind.annotation.*

@ResponseBody
@RequestMapping("/api")
class TestController {

    @RequestMapping(value = "/{name}", method = arrayOf(RequestMethod.GET))
    fun hello(@PathVariable(value = "name") name: String) = "Hello, $name!"

    @RequestMapping(value = "/", method = arrayOf(RequestMethod.GET))
    fun hello() = "Hello, User!"


}