package by.mksn.kwitapi.service.impl

import by.mksn.kwitapi.entity.Category
import by.mksn.kwitapi.entity.support.CategoryStats
import by.mksn.kwitapi.entity.support.CategoryType
import by.mksn.kwitapi.repository.CategoryRepository
import by.mksn.kwitapi.repository.TransactionRepository
import by.mksn.kwitapi.service.CategoryService
import by.mksn.kwitapi.wrapJPACall
import org.springframework.data.domain.Pageable
import java.sql.Timestamp

open class CategoryServiceImpl(
        private val categoryRepository: CategoryRepository,
        private val transactionRepository: TransactionRepository
) : AbstractCrudService<Category>(categoryRepository), CategoryService {

    override fun findAllByUserId(userId: Long, pageable: Pageable): List<Category> =
            wrapJPACall { categoryRepository.findByUserId(userId, pageable) }

    override fun findByUserIdAndType(id: Long, type: CategoryType, pageable: Pageable): List<Category> =
            wrapJPACall { categoryRepository.findByUserIdAndType(id, type, pageable) }

    override fun calculateCategoryStats(type: CategoryType,
                                        currencyCode: String,
                                        startDate: Timestamp,
                                        endDate: Timestamp): List<CategoryStats> {
        return emptyList()//wrapJPACall { categoryRepository.calculateCategoryStats(type, currencyId, startDate, endDate) }
    }

}