package by.mksn.kwitapi.service.impl

import by.mksn.kwitapi.entity.Category
import by.mksn.kwitapi.entity.support.CategoryStats
import by.mksn.kwitapi.entity.support.CategoryType
import by.mksn.kwitapi.repository.CategoryRepository
import by.mksn.kwitapi.repository.TransactionRepository
import by.mksn.kwitapi.service.CategoryService
import by.mksn.kwitapi.service.exception.ServiceException
import by.mksn.kwitapi.service.exception.ServiceNotFoundException
import by.mksn.kwitapi.support.TimestampRange
import by.mksn.kwitapi.support.ifNull
import by.mksn.kwitapi.support.ifNullServiceNotFound
import by.mksn.kwitapi.support.wrapJPACall
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = arrayOf(ServiceException::class))
class CategoryServiceImpl(
        private val categoryRepository: CategoryRepository,
        private val transactionRepository: TransactionRepository
) : AbstractCrudService<Category>(categoryRepository), CategoryService {

    companion object {
        private val logger = LoggerFactory.getLogger(CategoryServiceImpl::class.java)!!
    }

    override fun update(entity: Category): Category? {
        val databaseCategory = findByIdAndUserId(entity.id!!, entity.userId!!)
        databaseCategory.ifNullServiceNotFound(entity.id)
        databaseCategory!!.name = entity.name
        return wrapJPACall { categoryRepository.save(entity) }
    }

    override fun delete(id: Long, userId: Long) {
        checkPersonalVisibility(userId, id)
        val affected = wrapJPACall { transactionRepository.deleteByCategoryId(id) }
        logger.info("$affected transactions deleted from category[$id]")
        wrapJPACall { categoryRepository.delete(id) }
    }

    override fun softDelete(id: Long, newId: Long, userId: Long) {
        val newCategory = wrapJPACall { categoryRepository.findByIdAndUserId(newId, userId) }
        newCategory.ifNull { throw ServiceNotFoundException("New category" to "Category with id '$newId' not found.") }
        val affected = wrapJPACall { transactionRepository.shiftToNewCategory(newId, id) }
        logger.info("$affected transactions shifted from category[$id] to category[$newId, ${newCategory?.name}]")
        wrapJPACall { categoryRepository.delete(id) }
    }

    override fun findAllByUserId(userId: Long, pageable: Pageable): List<Category> =
            wrapJPACall { categoryRepository.findByUserId(userId, pageable) }

    override fun findByUserIdAndType(id: Long, type: CategoryType, pageable: Pageable): List<Category> =
            wrapJPACall { categoryRepository.findByUserIdAndType(id, type, pageable) }

    override fun calculateCategoryStats(type: CategoryType,
                                        currencyCode: String,
                                        range: TimestampRange?): List<CategoryStats> {
        return emptyList()//wrapJPACall { categoryRepository.calculateCategoryStats(type, currencyId, startDate, endDate) }
    }

}