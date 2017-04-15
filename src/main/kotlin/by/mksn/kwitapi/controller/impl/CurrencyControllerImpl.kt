package by.mksn.kwitapi.controller.impl

import by.mksn.kwitapi.configuration.security.UserAuth
import by.mksn.kwitapi.configuration.security.UserDetails
import by.mksn.kwitapi.controller.CurrencyController
import by.mksn.kwitapi.controller.exception.AccessDeniedException
import by.mksn.kwitapi.controller.exception.NotFoundException
import by.mksn.kwitapi.entity.Currency
import by.mksn.kwitapi.service.CurrencyService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid

open class CurrencyControllerImpl(
        private val currencyService: CurrencyService
) : CurrencyController {
    override fun create(@Valid @RequestBody entity: Currency, @UserAuth auth: UserDetails): Currency
            = if (auth.isAdmin) currencyService.create(entity) else throw AccessDeniedException()

    override fun findByCode(code: String): Currency
            = currencyService.findByCode(code) ?: throw NotFoundException()

    override fun findById(@PathVariable("id") id: Long, @UserAuth auth: UserDetails): Currency
            = currencyService.findById(id) ?: throw NotFoundException()

    override fun update(@PathVariable("id") id: Long, @Valid @RequestBody entity: Currency, @UserAuth auth: UserDetails): Currency
            = if (auth.isAdmin) currencyService.update(id, entity) else throw AccessDeniedException()

    override fun delete(@PathVariable("id") id: Long, @UserAuth auth: UserDetails) {
        if (auth.isAdmin) currencyService.delete(id) else throw AccessDeniedException()
    }

    override fun findAll(@UserAuth auth: UserDetails, pageable: Pageable): List<Currency>
            = currencyService.findAll(pageable)
}