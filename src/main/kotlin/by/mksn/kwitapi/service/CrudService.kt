package by.mksn.kwitapi.service

import by.mksn.kwitapi.entity.IdSetable
import java.io.Serializable

interface CrudService<E : IdSetable<ID>, in ID : Serializable> {
    fun findById(id: ID): E?
    fun create(entity: E): E
    fun update(entity: E, id: ID): E
    fun delete(id: ID)
}