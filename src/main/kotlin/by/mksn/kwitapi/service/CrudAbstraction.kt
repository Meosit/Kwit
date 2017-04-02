package by.mksn.kwitapi.service

import by.mksn.kwitapi.model.EntityIdSetable
import org.springframework.data.repository.CrudRepository
import java.io.Serializable

interface CrudService<E : EntityIdSetable, in ID> {

    fun findById(id: ID): E?

    fun create(entity: E): E

    fun update(entity: E, id: Long): E

    fun delete(id: ID)
}

abstract class AbstractCrudService<E : EntityIdSetable, in ID : Serializable>(val crudRepository: CrudRepository<E, in ID>) : CrudService<E, ID> {

    override fun findById(id: ID): E? = crudRepository.findOne(id)

    override fun create(entity: E): E {
        entity.setEntityId(null)
        return crudRepository.save(entity)
    }

    override fun update(entity: E, id: Long): E {
        entity.setEntityId(id)
        return crudRepository.save(entity)
    }

    override fun delete(id: ID) = crudRepository.delete(id)

}