package by.mksn.kwitapi.service

import by.mksn.kwitapi.entity.Category
import by.mksn.kwitapi.entity.support.CategoriesStats
import by.mksn.kwitapi.entity.support.CategoryType
import by.mksn.kwitapi.support.DateRange

interface CategoryService : PersonalCrudService<Category, Long> {

    fun findByUserIdAndType(id: Long, type: CategoryType): List<Category>

    fun findAllByUserId(userId: Long): List<Category>

    fun softDelete(id: Long, newId: Long, userId: Long): Unit?

    fun calculateCategoryStats(
            userId: Long,
            type: CategoryType,
            currencyCode: String,
            range: DateRange?
    ): CategoriesStats
}