package by.mksn.kwitapi.controller.impl

import by.mksn.kwitapi.configuration.security.Auth
import by.mksn.kwitapi.configuration.security.UserDetails
import by.mksn.kwitapi.controller.CrudController
import by.mksn.kwitapi.controller.exception.BadRequestException
import by.mksn.kwitapi.controller.exception.NotFoundException
import by.mksn.kwitapi.entity.support.IdAndUserIdAssignable
import by.mksn.kwitapi.service.PersonalCrudService
import by.mksn.kwitapi.wrapServiceCall
import org.slf4j.Logger
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid

abstract class AbstractPersonalCrudController<E : IdAndUserIdAssignable<Long>>(
        private val crudService: PersonalCrudService<E, Long>,
        private val logger: Logger
) : CrudController<E, Long> {

    private fun isAccessDenied(auth: UserDetails, entityId: Long) =
            crudService.findByIdAndUserId(entityId, auth.userId) == null

    override fun create(@Valid @RequestBody entity: E, @Auth auth: UserDetails): E = wrapServiceCall(logger) {
        entity.assignID(null)
        entity.assignUserID(auth.userId)
        crudService.create(entity) ?: throw BadRequestException()
    }

    override fun findById(@PathVariable("id") id: Long, @Auth auth: UserDetails): E = wrapServiceCall(logger) {
        crudService.findByIdAndUserId(id, auth.userId) ?: throw NotFoundException()
    }

    override fun update(@PathVariable("id") id: Long, @Valid @RequestBody entity: E, @Auth auth: UserDetails): E {
        if (isAccessDenied(auth, id)) throw NotFoundException()
        entity.assignID(id)
        entity.assignUserID(auth.userId)
        return wrapServiceCall(logger) { crudService.update(entity) ?: throw BadRequestException() }
    }


    override fun delete(@PathVariable("id") id: Long, @Auth auth: UserDetails) {
        if (isAccessDenied(auth, id)) throw NotFoundException()
        wrapServiceCall(logger) { crudService.delete(id) }
    }


    override fun findAll(@Auth auth: UserDetails, pageable: Pageable): List<E> =
            wrapServiceCall(logger) { crudService.findAllByUserId(auth.userId, pageable) }

}