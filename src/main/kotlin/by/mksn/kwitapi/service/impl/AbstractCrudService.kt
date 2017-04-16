package by.mksn.kwitapi.service.impl

import by.mksn.kwitapi.entity.IdAndUserIdAssignable
import by.mksn.kwitapi.repository.PersonalCrudRepository
import by.mksn.kwitapi.service.PersonalCrudService
import by.mksn.kwitapi.wrapJPACall

abstract class AbstractCrudService<E : IdAndUserIdAssignable<Long>>(
        private val crudRepository: PersonalCrudRepository<E, Long>
) : PersonalCrudService<E, Long> {

    override fun findByIdAndUserId(id: Long, userId: Long): E? =
            wrapJPACall { crudRepository.findByIdAndUserId(id, userId) }

    override fun create(userId: Long, entity: E): E? = wrapJPACall {
        entity.assignID(null)
        entity.assignUserID(userId)
        crudRepository.save(entity)
    }

    override fun update(userId: Long, id: Long, entity: E): E? = wrapJPACall {
        entity.assignID(id)
        entity.assignUserID(userId)
        crudRepository.save(entity)
    }

    override fun delete(id: Long) = wrapJPACall { crudRepository.delete(id) }

}