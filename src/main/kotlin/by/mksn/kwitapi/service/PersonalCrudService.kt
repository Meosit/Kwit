package by.mksn.kwitapi.service

import by.mksn.kwitapi.entity.IdSetable
import org.springframework.data.domain.Pageable
import java.io.Serializable

interface PersonalCrudService<E : IdSetable<ID>, in ID : Serializable> {

    fun findByIdAndUserId(id: ID, userId: ID): E?

    fun findAllByUserId(userId: ID, pageable: Pageable): List<E>

    fun create(userId: ID, entity: E): E?

    fun update(id: ID, userId: ID, entity: E): E?

    fun delete(id: ID, userId: ID)
}