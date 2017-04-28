package by.mksn.kwitapi.service

import by.mksn.kwitapi.entity.Category
import by.mksn.kwitapi.entity.support.CategoryStats
import by.mksn.kwitapi.entity.support.CategoryType
import org.springframework.data.domain.Pageable
import java.sql.Timestamp


interface CategoryService : PersonalCrudService<Category, Long> {

    fun findByUserIdAndType(id: Long, type: CategoryType, pageable: Pageable): List<Category>

    fun calculateCategoryStats(
            type: CategoryType,
            currencyCode: String,
            startDate: Timestamp,
            endDate: Timestamp
    ): List<CategoryStats>
}