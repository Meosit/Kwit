package by.mksn.kwit.service.impl

import by.mksn.kwit.entity.Category
import by.mksn.kwit.entity.support.CategoriesStats
import by.mksn.kwit.entity.support.CategoryType
import by.mksn.kwit.repository.CategoryRepository
import by.mksn.kwit.repository.CurrencyRepository
import by.mksn.kwit.repository.TransactionRepository
import by.mksn.kwit.service.CategoryService
import by.mksn.kwit.service.exception.ServiceBadRequestException
import by.mksn.kwit.service.exception.ServiceException
import by.mksn.kwit.service.exception.ServiceNotFoundException
import by.mksn.kwit.support.*
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = arrayOf(ServiceException::class))
class CategoryServiceImpl(
        private val categoryRepository: CategoryRepository,
        private val transactionRepository: TransactionRepository,
        private val currencyRepository: CurrencyRepository
) : AbstractCrudService<Category>(categoryRepository), CategoryService {

    companion object {
        private val logger = LoggerFactory.getLogger(CategoryServiceImpl::class.java)!!
    }

    override fun update(entity: Category): Category? {
        val databaseCategory = findByIdAndUserId(entity.id!!, entity.userId!!)
        databaseCategory.ifNullServiceNotFound(entity.id)
        databaseCategory!!.name = entity.name
        return wrapJPAModifyingCall { categoryRepository.save(entity) }
    }

    override fun findAllByUserId(userId: Long): List<Category> =
            wrapJPACall { categoryRepository.findByUserIdOrderByIdAsc(userId) }

    override fun findAllByUserId(userId: Long, pageable: Pageable): Page<Category> =
            wrapJPACall { categoryRepository.findByUserIdOrderByIdAsc(userId, pageable) }

    override fun findByUserIdAndType(id: Long, type: CategoryType): List<Category> =
            wrapJPACall { categoryRepository.findByUserIdAndType(id, type) }

    override fun calculateCategoryStats(userId: Long, type: CategoryType, currencyCode: String, range: DateRange?): CategoriesStats {
        val (from, to) = range ?: DateRange.NONE
        val currency = wrapJPACall { currencyRepository.findByCode(currencyCode) }
                ?: throw ServiceNotFoundException("Error" to "Currency with code '$currencyCode' not found")
        val categories = wrapJPACall {
            categoryRepository.fetchCategoryStats(userId, currency.id!!,
                    type.name, from.toStamp(), to.toStamp())
        }
        logger.debug("Fetched ${categories.size} categories stats for currency '" +
                "$currencyCode' and period: ${if (range == null) "All time" else "${range.start} to ${range.end}"}")
        val categoriesStats = CategoriesStats(currency, type, range, categories)
        return categoriesStats
    }

    override fun delete(id: Long, userId: Long): Unit? {
        checkPersonalVisibility(userId, id)
        val affected = wrapJPACall { transactionRepository.deleteByCategoryId(id) }
        logger.info("$affected transactions deleted from category[$id]")
        val isSuccess = wrapJPAModifyingCall { categoryRepository.delete(id) }
        if (isSuccess != null) logger.info("Category[$id] deleted.")
        return isSuccess
    }

    override fun softDelete(id: Long, newId: Long, userId: Long): Unit? {
        checkPersonalVisibility(userId, id)
        if (id == userId) throw ServiceBadRequestException("Error" to "Cannot shift transactions to delete category")
        val newCategory = wrapJPACall { categoryRepository.findByIdAndUserId(newId, userId) }
        newCategory ?: throw ServiceNotFoundException("New category" to "Category with id '$newId' not found.")
        val affected = wrapJPACall { transactionRepository.shiftToNewCategory(newId, id) }
        logger.info("$affected transactions shifted from category[$id] to category[$newId, ${newCategory.name}]")
        val isSuccess = wrapJPAModifyingCall { categoryRepository.delete(id) }
        if (isSuccess != null) logger.info("Category[$id] deleted.")
        return isSuccess
    }

}