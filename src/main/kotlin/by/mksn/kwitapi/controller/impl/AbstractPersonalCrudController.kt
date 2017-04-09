package by.mksn.kwitapi.controller.impl

import by.mksn.kwitapi.configuration.security.UserAuth
import by.mksn.kwitapi.configuration.security.UserDetails
import by.mksn.kwitapi.controller.PersonalCrudController
import by.mksn.kwitapi.controller.exception.BadRequestException
import by.mksn.kwitapi.controller.exception.NotFoundException
import by.mksn.kwitapi.entity.IdSetable
import by.mksn.kwitapi.service.PersonalCrudService
import by.mksn.kwitapi.wrapServiceCall
import org.slf4j.Logger
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid

abstract class AbstractPersonalCrudController<E : IdSetable<Long>>(
        private val crudService: PersonalCrudService<E, Long>,
        private val logger: Logger
) : PersonalCrudController<E, Long> {

    override fun create(@Valid @RequestBody entity: E, @UserAuth auth: UserDetails): E =
            wrapServiceCall(logger) {
                crudService.create(auth.userId, entity) ?: throw BadRequestException()
            }

    override fun findById(@PathVariable("id") id: Long, @UserAuth auth: UserDetails): E =
            wrapServiceCall(logger) {
                crudService.findByIdAndUserId(id, auth.userId) ?: throw NotFoundException()
            }

    override fun update(@PathVariable("id") id: Long, @Valid @RequestBody entity: E, @UserAuth auth: UserDetails): E =
            wrapServiceCall(logger) {
                crudService.update(id, auth.userId, entity) ?: throw NotFoundException()
            }

    override fun delete(@PathVariable("id") id: Long, @UserAuth auth: UserDetails) =
            wrapServiceCall(logger) { crudService.delete(id, auth.userId) }

    override fun findAll(@UserAuth auth: UserDetails, pageable: Pageable): List<E> =
            wrapServiceCall(logger) { crudService.findAllByUserId(auth.userId, pageable) }
}