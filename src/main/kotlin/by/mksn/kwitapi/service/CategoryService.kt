package by.mksn.kwitapi.service

import by.mksn.kwitapi.entity.Category
import by.mksn.kwitapi.entity.support.CategoryStats
import by.mksn.kwitapi.entity.support.CategoryType
import by.mksn.kwitapi.support.TimestampRange
import org.springframework.data.domain.Pageable

interface CategoryService : PersonalCrudService<Category, Long> {

    fun findByUserIdAndType(id: Long, type: CategoryType, pageable: Pageable): List<Category>

    fun softDelete(id: Long, newId: Long, userId: Long)

    fun calculateCategoryStats(
            type: CategoryType,
            currencyCode: String,
            range: TimestampRange?
    ): List<CategoryStats>
}