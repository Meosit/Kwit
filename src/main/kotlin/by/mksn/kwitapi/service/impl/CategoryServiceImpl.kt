package by.mksn.kwitapi.service.impl

import by.mksn.kwitapi.entity.Category
import by.mksn.kwitapi.entity.CategoryStats
import by.mksn.kwitapi.repository.CategoryRepository
import by.mksn.kwitapi.service.CategoryService
import org.springframework.data.domain.Pageable
import java.sql.Timestamp

open class CategoryServiceImpl(
        private val categoryRepository: CategoryRepository
) : CategoryService {
    override fun findByIdAndUserId(id: Long, userId: Long): Category?
            = categoryRepository.findByIdAndUserId(id, userId)

    override fun findByUserIdAndType(id: Long, type: Category.Type, pageable: Pageable): List<Category>
            = categoryRepository.findByUserIdAndType(id, type, pageable)

    override fun findAllByUserId(userId: Long, pageable: Pageable): List<Category>
            = categoryRepository.findByUserId(userId, pageable)

    override fun create(userId: Long, entity: Category): Category? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun calculateCategoryStats(type: Category.Type, currencyId: Long, startDate: Timestamp, endDate: Timestamp): List<CategoryStats> =
            emptyList()//wrapJPACall { categoryRepository.calculateCategoryStats(type, currencyId, startDate, endDate) }

    override fun update(id: Long, userId: Long, entity: Category): Category? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(id: Long, userId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}