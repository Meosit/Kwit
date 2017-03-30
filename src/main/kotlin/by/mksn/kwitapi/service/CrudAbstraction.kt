package by.mksn.kwitapi.service

import org.springframework.data.repository.CrudRepository
import java.io.Serializable

interface CrudService<E, in ID> {

    fun findById(id: ID): E?

    fun save(entity: E): E

    fun delete(id: ID)
}

abstract class AbstractCrudService<E, in ID : Serializable>(val crudRepository: CrudRepository<E, in ID>) : CrudService<E, ID> {

    override fun findById(id: ID): E? = tryCallJPA { crudRepository.findOne(id) }

    override fun save(entity: E): E = tryCallJPA { crudRepository.save(entity) }

    override fun delete(id: ID) = tryCallJPA { crudRepository.delete(id) }

}