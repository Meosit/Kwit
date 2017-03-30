package by.mksn.kwitapi.controller

import by.mksn.kwitapi.service.CrudService
import by.mksn.kwitapi.service.ServiceException
import org.slf4j.Logger
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
interface CrudController<E, in ID> {

    @GetMapping("{id}")
    fun findById(@PathVariable("id") id: ID): E?

    @PostMapping("save")
    fun save(@RequestBody entity: E): E

    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") id: ID)
}

abstract class AbstractCrudController<E, in ID>(val crudService: CrudService<E, ID>, val logger: Logger) : CrudController<E, ID> {

    override fun findById(@PathVariable("id") id: ID): E {
        try {
            val entity = crudService.findById(id)
            return if (entity != null) {
                logger.trace("User with id $id has been found")
                entity
            } else {
                logger.trace("User with id $id not found")
                throw UserNotFoundException("User with id $id not found")
            }
        } catch (e: ServiceException) {
            logger.warn("Cannot find entity with id " + id + "\n", e)
            throw ControllerException(cause = e)
        }
    }

    override fun save(@Valid @RequestBody entity: E): E = crudService.save(entity)

    override fun delete(@PathVariable("id") id: ID) {
        try {
            crudService.delete(id)
            logger.trace("User with id $id has been deleted")
        } catch (e: ServiceException) {
            logger.warn("Cannot delete entity with id " + id + "\n", e)
            throw ControllerException(cause = e)
        }
    }

}