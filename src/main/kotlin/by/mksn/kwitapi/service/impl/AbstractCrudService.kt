package by.mksn.kwitapi.service.impl

import by.mksn.kwitapi.entity.support.IdAndUserIdAssignable
import by.mksn.kwitapi.repository.PersonalCrudRepository
import by.mksn.kwitapi.service.PersonalCrudService
import by.mksn.kwitapi.wrapJPACall

abstract class AbstractCrudService<E : IdAndUserIdAssignable<Long>>(
        private val crudRepository: PersonalCrudRepository<E, Long>
) : PersonalCrudService<E, Long> {

    override fun findByIdAndUserId(id: Long, userId: Long): E? =
            wrapJPACall { crudRepository.findByIdAndUserId(id, userId) }

    override fun create(entity: E): E? =
            wrapJPACall { crudRepository.save(entity) }

    override fun update(entity: E): E? =
            wrapJPACall { crudRepository.save(entity) }

    override fun delete(id: Long) =
            wrapJPACall { crudRepository.delete(id) }

}