package by.mksn.kwitapi.controller.impl

import by.mksn.kwitapi.configuration.security.UserAuth
import by.mksn.kwitapi.configuration.security.UserDetails
import by.mksn.kwitapi.controller.CategoryController
import by.mksn.kwitapi.entity.Category
import by.mksn.kwitapi.entity.CategoryStats
import by.mksn.kwitapi.service.CategoryService
import by.mksn.kwitapi.wrapServiceCall
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.PathVariable
import java.sql.Timestamp

open class CategoryControllerImpl(
        private val categoryService: CategoryService
) : AbstractPersonalCrudController<Category>(categoryService, logger), CategoryController {

    companion object {

        private val logger = LoggerFactory.getLogger(CategoryControllerImpl::class.java)!!
    }

    override fun calculateCategoryStats(type: Category.Type, currencyId: Long, startDate: Timestamp, endDate: Timestamp): List<CategoryStats> =
            wrapServiceCall(logger) { categoryService.calculateCategoryStats(type, currencyId, startDate, endDate) }

    override fun findAll(@UserAuth auth: UserDetails,
                         @PathVariable("type") type: Category.Type,
                         pageable: Pageable): List<Category> =
            wrapServiceCall(logger) { categoryService.findByUserIdAndType(auth.userId, type, pageable) }
}