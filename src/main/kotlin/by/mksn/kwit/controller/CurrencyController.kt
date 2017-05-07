package by.mksn.kwit.controller

import by.mksn.kwit.configuration.security.Auth
import by.mksn.kwit.configuration.security.UserDetails
import by.mksn.kwit.entity.Currency
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/currencies")
interface CurrencyController : CrudController<Currency, Long> {

    @GetMapping("code/{code}")
    fun findByCode(@PathVariable("code") code: String): Currency

    @GetMapping("all")
    fun findAll(@Auth auth: UserDetails): Iterable<Currency>

}