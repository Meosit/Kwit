package by.mksn.kwitapi.service

import by.mksn.kwitapi.entity.Category
import java.awt.print.Pageable


interface CategoryService : CrudService<Category, Long> {
    fun findByUserId(id: Long, pageable: Pageable): List<Category>
    fun findByUserIdAndIsIncome(id: Long, isIncome: Boolean, pageable: Pageable): List<Category>
}