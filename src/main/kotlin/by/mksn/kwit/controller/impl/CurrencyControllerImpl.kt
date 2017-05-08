package by.mksn.kwit.controller.impl

import by.mksn.kwit.configuration.security.Auth
import by.mksn.kwit.configuration.security.UserDetails
import by.mksn.kwit.controller.CurrencyController
import by.mksn.kwit.entity.Currency
import by.mksn.kwit.entity.support.CurrencyCode
import by.mksn.kwit.service.CurrencyService
import by.mksn.kwit.support.ifNullNotFound
import by.mksn.kwit.support.notFoundException
import by.mksn.kwit.support.wrapServiceCall
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
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

    override fun getAll(@Auth auth: UserDetails): Iterable<Currency> =
            wrapServiceCall(logger) { currencyService.findAll() }

    override fun getAll(@Auth auth: UserDetails, pageable: Pageable): Page<Currency> =
            wrapServiceCall(logger) { currencyService.findAll(pageable) }

    override fun getById(@PathVariable("id") id: Long?, @Auth auth: UserDetails): Currency =
            wrapServiceCall(logger) { currencyService.findById(id!!).ifNullNotFound(id) }

    override fun getByCode(@PathVariable("code") @CurrencyCode code: String): Currency =
            wrapServiceCall(logger) {
                currencyService.findByCode(code)
                        ?: notFoundException("Error", "Entity with code '$code' not found")
            }

    override fun create(@Valid @RequestBody entity: Currency, @Auth auth: UserDetails): Currency =
            wrapServiceCall(logger) { currencyService.create(entity) }

    override fun update(@PathVariable("id") id: Long?, @Valid @RequestBody entity: Currency, @Auth auth: UserDetails): Currency =
            wrapServiceCall(logger) { currencyService.update(id!!, entity) }

    override fun delete(@PathVariable("id") id: Long?, @Auth auth: UserDetails) =
            wrapServiceCall(logger) { currencyService.delete(id!!) }
}