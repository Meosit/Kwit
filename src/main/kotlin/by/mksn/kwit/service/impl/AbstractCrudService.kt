package by.mksn.kwit.service.impl

import by.mksn.kwit.entity.support.UserBaseEntity
import by.mksn.kwit.repository.PersonalCrudRepository
import by.mksn.kwit.service.PersonalCrudService
import by.mksn.kwit.service.exception.ServiceException
import by.mksn.kwit.support.ifNullServiceNotFound
import by.mksn.kwit.support.wrapJPACall
import by.mksn.kwit.support.wrapJPAModifyingCall
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = arrayOf(ServiceException::class))
abstract class AbstractCrudService<E : UserBaseEntity<Long>>(
        private val crudRepository: PersonalCrudRepository<E, Long>
) : PersonalCrudService<E, Long> {

    @Transactional(propagation = Propagation.SUPPORTS)
    protected open fun checkValidNestedEntitiesIfNeed(entity: E) {}

    @Transactional(propagation = Propagation.SUPPORTS)
    protected fun checkPersonalVisibility(userId: Long, entityId: Long) =
            findByIdAndUserId(entityId, userId).ifNullServiceNotFound(entityId)

    override fun findByIdAndUserId(id: Long, userId: Long): E? =
            wrapJPACall { crudRepository.findByIdAndUserId(id, userId) }

    override fun create(entity: E): E? = wrapJPACall {
        checkPersonalVisibility(entity.userId!!, entity.id!!)
        checkValidNestedEntitiesIfNeed(entity)
        wrapJPAModifyingCall { crudRepository.save(entity) }
    }

    override fun update(entity: E): E? = wrapJPACall {
        checkPersonalVisibility(entity.userId!!, entity.id!!)
        checkValidNestedEntitiesIfNeed(entity)
        wrapJPAModifyingCall { crudRepository.save(entity) }
    }

    override fun delete(id: Long, userId: Long): Unit? = wrapJPACall {
        checkPersonalVisibility(userId, id)
        wrapJPAModifyingCall { crudRepository.delete(id) }
    }

}