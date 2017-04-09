package by.mksn.kwitapi.service

import by.mksn.kwitapi.entity.Category
import java.awt.print.Pageable


interface CategoryService : PersonalCrudService<Category, Long> {

    fun findByUserIdAndType(id: Long, isIncome: Boolean, pageable: Pageable): List<Category>

}