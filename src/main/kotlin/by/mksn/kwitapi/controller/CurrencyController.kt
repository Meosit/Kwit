package by.mksn.kwitapi.controller

import by.mksn.kwitapi.entity.Currency
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/currency")
interface CurrencyController : CrudController<Currency, Long> {

    @GetMapping("{code}")
    fun findByCode(@PathVariable("code") code: String): Currency

}