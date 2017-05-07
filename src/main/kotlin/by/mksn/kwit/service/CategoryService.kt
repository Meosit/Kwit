package by.mksn.kwit.service

import by.mksn.kwit.entity.Category
import by.mksn.kwit.entity.support.CategoriesStats
import by.mksn.kwit.entity.support.CategoryType
import by.mksn.kwit.support.DateRange

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