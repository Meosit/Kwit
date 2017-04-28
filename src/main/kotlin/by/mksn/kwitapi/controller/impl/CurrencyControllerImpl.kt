package by.mksn.kwitapi.controller.impl

import by.mksn.kwitapi.configuration.security.Auth
import by.mksn.kwitapi.configuration.security.UserDetails
import by.mksn.kwitapi.controller.CurrencyController
import by.mksn.kwitapi.controller.exception.NotFoundException
import by.mksn.kwitapi.entity.Currency
import by.mksn.kwitapi.service.CurrencyService
import by.mksn.kwitapi.wrapServiceCall
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid

open class CurrencyControllerImpl(
        private val currencyService: CurrencyService
) : CurrencyController {

    companion object {
        private val logger = LoggerFactory.getLogger(CurrencyControllerImpl::class.java)!!
    }

    override fun create(@Valid @RequestBody entity: Currency, @Auth auth: UserDetails): Currency
            = wrapServiceCall(logger) { currencyService.create(entity) }

    override fun findByCode(@PathVariable("code") code: String): Currency
            = wrapServiceCall(logger) { currencyService.findByCode(code) ?: throw NotFoundException() }

    override fun findById(@PathVariable("id") id: Long, @Auth auth: UserDetails): Currency
            = wrapServiceCall(logger) { currencyService.findById(id) ?: throw NotFoundException() }

    override fun update(@PathVariable("id") id: Long, @Valid @RequestBody entity: Currency, @Auth auth: UserDetails): Currency
            = wrapServiceCall(logger) { currencyService.update(id, entity) }

    override fun delete(@PathVariable("id") id: Long, @Auth auth: UserDetails)
            = wrapServiceCall(logger) { currencyService.delete(id) }

    override fun findAll(@Auth auth: UserDetails, pageable: Pageable): List<Currency>
            = wrapServiceCall(logger) { currencyService.findAll(pageable) }
}