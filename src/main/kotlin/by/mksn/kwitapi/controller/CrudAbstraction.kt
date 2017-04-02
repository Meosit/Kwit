package by.mksn.kwitapi.controller

import by.mksn.kwitapi.exception.UserNotFoundException
import by.mksn.kwitapi.model.EntityIdSetable
import by.mksn.kwitapi.service.CrudService
import org.slf4j.Logger
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
interface CrudController<E, in ID> {

    @PostMapping("")
    fun create(@Valid @RequestBody entity: E): E

    @GetMapping("{id}")
    fun findById(@PathVariable("id") id: ID): E?

    @PutMapping("{id}")
    fun update(@PathVariable("id") id: Long, @Valid @RequestBody entity: E): E

    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") id: ID)
}

abstract class AbstractCrudController<E : EntityIdSetable, in ID>(val crudService: CrudService<E, ID>, val logger: Logger) : CrudController<E, ID> {

    override fun create(@Valid @RequestBody entity: E): E =
            crudService.create(entity)

    override fun findById(@PathVariable("id") id: ID): E {
        val entity = crudService.findById(id)
        return if (entity != null) {
            logger.trace("User with id $id has been found")
            entity
        } else {
            logger.trace("User with id $id not found")
            throw UserNotFoundException("User with id $id not found")
        }
    }

    override fun update(@PathVariable("id") id: Long, @Valid @RequestBody entity: E): E {
        entity.setEntityId(id)
        return crudService.create(entity)
    }

    override fun delete(@PathVariable("id") id: ID) =
            crudService.delete(id)

}