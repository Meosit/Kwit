package by.mksn.kwitapi.service

import by.mksn.kwitapi.entity.support.BaseEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.io.Serializable

interface PersonalCrudService<E : BaseEntity<ID>, ID : Serializable> {

    fun findByIdAndUserId(id: ID, userId: ID): E?

    fun findAllByUserId(userId: ID, pageable: Pageable): Page<E>

    fun create(entity: E): E?

    fun update(entity: E): E?

    fun delete(id: ID, userId: ID): Unit?
}