package by.mksn.kwit.controller.impl

import by.mksn.kwit.configuration.security.Auth
import by.mksn.kwit.configuration.security.UserDetails
import by.mksn.kwit.controller.CrudController
import by.mksn.kwit.entity.support.UserBaseEntity
import by.mksn.kwit.service.PersonalCrudService
import by.mksn.kwit.support.badRequestException
import by.mksn.kwit.support.ifNullNotFound
import by.mksn.kwit.support.notFoundException
import by.mksn.kwit.support.wrapServiceCall
import org.slf4j.Logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid

abstract class AbstractPersonalCrudController<E : UserBaseEntity<Long>>(
        private val crudService: PersonalCrudService<E, Long>,
        private val logger: Logger
) : CrudController<E, Long> {


    override fun getAll(@Auth auth: UserDetails, pageable: Pageable): Page<E> =
            wrapServiceCall(logger) { crudService.findAllByUserId(auth.userId, pageable) }

    override fun getById(@PathVariable("id") id: Long?, @Auth auth: UserDetails): E = wrapServiceCall(logger) {
        crudService.findByIdAndUserId(id!!, auth.userId).ifNullNotFound(id)
    }

    override fun create(@Valid @RequestBody entity: E, @Auth auth: UserDetails): E = wrapServiceCall(logger) {
        entity.id = null
        entity.userId = auth.userId
        crudService.create(entity) ?: throw badRequestException("Error", "Cannot create entity")
    }

    override fun update(@PathVariable("id") id: Long?, @Valid @RequestBody entity: E, @Auth auth: UserDetails): E {
        entity.id = id
        entity.userId = auth.userId
        return wrapServiceCall(logger) {
            crudService.update(entity) ?:
                    throw badRequestException("Error", "Cannot create entity")
        }
    }

    override fun delete(@PathVariable("id") id: Long?, @Auth auth: UserDetails) =
            wrapServiceCall(logger) { crudService.delete(id!!, auth.userId) }
                    ?: notFoundException("Error", "Entity not found")

}