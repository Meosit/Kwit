package by.mksn.kwitapi.service

import by.mksn.kwitapi.entity.Category
import by.mksn.kwitapi.entity.CategoryStats
import org.springframework.data.domain.Pageable
import java.sql.Timestamp


interface CategoryService : PersonalCrudService<Category, Long> {

    fun findByUserIdAndType(id: Long, type: Category.Type, pageable: Pageable): List<Category>

    fun calculateCategoryStats(
            type: Category.Type, currencyId: Long,
            startDate: Timestamp, endDate: Timestamp
    ): List<CategoryStats>
}