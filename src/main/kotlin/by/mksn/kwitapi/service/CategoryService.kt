package by.mksn.kwitapi.service

import by.mksn.kwitapi.entity.Category
import org.springframework.data.domain.Pageable


interface CategoryService : PersonalCrudService<Category, Long> {

    fun findByUserIdAndType(id: Long, type: Category.Type, pageable: Pageable): List<Category>

}