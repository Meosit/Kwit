package by.mksn.kwitapi.service

import by.mksn.kwitapi.entity.IdAssignable
import org.springframework.data.domain.Pageable
import java.io.Serializable

interface PersonalCrudService<E : IdAssignable<ID>, in ID : Serializable> {

    fun findByIdAndUserId(id: ID, userId: ID): E?

    fun findAllByUserId(userId: ID, pageable: Pageable): List<E>

    fun create(userId: Long, entity: E): E?

    fun update(userId: Long, id: ID, entity: E): E?

    fun delete(id: ID)
}