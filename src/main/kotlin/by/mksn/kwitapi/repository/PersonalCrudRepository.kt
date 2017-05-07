package by.mksn.kwitapi.repository

import by.mksn.kwitapi.entity.support.BaseEntity
import java.io.Serializable

interface PersonalCrudRepository<E : BaseEntity<ID>, ID : Serializable> {
    fun findByIdAndUserId(id: ID, userId: ID): E?
    fun <S : E> save(entity: S): S
    fun delete(id: ID)
}
