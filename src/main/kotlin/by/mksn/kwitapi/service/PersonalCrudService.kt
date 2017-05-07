package by.mksn.kwitapi.service

import by.mksn.kwitapi.entity.support.IdAssignable
import org.springframework.data.domain.Pageable
import java.io.Serializable

interface PersonalCrudService<E : IdAssignable<ID>, ID : Serializable> {

    fun findByIdAndUserId(id: ID, userId: ID): E?

    fun findAllByUserId(userId: ID, pageable: Pageable): List<E>

    fun create(entity: E): E?

    fun update(entity: E): E?

    fun delete(id: ID, userId: ID): Unit?
}