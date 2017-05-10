package by.mksn.kwit.controller.impl

import by.mksn.kwit.configuration.security.Auth
import by.mksn.kwit.configuration.security.UserDetails
import by.mksn.kwit.controller.CategoryController
import by.mksn.kwit.entity.Category
import by.mksn.kwit.entity.support.CategoriesStats
import by.mksn.kwit.entity.support.CategoryType
import by.mksn.kwit.entity.support.CategoryTypeBinder
import by.mksn.kwit.entity.support.CurrencyCode
import by.mksn.kwit.service.CategoryService
import by.mksn.kwit.support.notFoundException
import by.mksn.kwit.support.till
import by.mksn.kwit.support.wrapServiceCall
import org.slf4j.LoggerFactory
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@Validated
open class CategoryControllerImpl(
        private val categoryService: CategoryService
) : AbstractPersonalCrudController<Category>(categoryService, logger), CategoryController {
    companion object {

        private val logger = LoggerFactory.getLogger(CategoryControllerImpl::class.java)!!
    }

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.registerCustomEditor(CategoryType::class.java, CategoryTypeBinder())
    }

    override fun calculateCategoryStats(
            @PathVariable("type") type: CategoryType,
            @PathVariable("currencyCode") @CurrencyCode currencyCode: String,
            @RequestParam(name = "from") @DateTimeFormat(pattern = "yyyy-MM-dd") from: Date,
            @RequestParam(name = "to") @DateTimeFormat(pattern = "yyyy-MM-dd") to: Date,
            @Auth auth: UserDetails
    ): CategoriesStats =
            wrapServiceCall(logger) { categoryService.calculateCategoryStats(auth.userId, type, currencyCode, from till to) }

    override fun calculateCategoryStatsAllTime(
            @PathVariable("type") type: CategoryType,
            @PathVariable("currencyCode") @CurrencyCode currencyCode: String,
            @Auth auth: UserDetails
    ): CategoriesStats =
            wrapServiceCall(logger) { categoryService.calculateCategoryStats(auth.userId, type, currencyCode, null) }

    override fun getAll(@Auth auth: UserDetails): List<Category> =
            wrapServiceCall(logger) { categoryService.findAllByUserId(auth.userId) }

    override fun getAll(@Auth auth: UserDetails, @PathVariable("type") type: CategoryType): List<Category> =
            wrapServiceCall(logger) { categoryService.findByUserIdAndType(auth.userId, type) }

    override fun softDelete(@PathVariable("id") id: Long?, @RequestParam("newCategory") newId: Long?, @Auth auth: UserDetails) =
            wrapServiceCall(logger) { categoryService.softDelete(id!!, newId!!, auth.userId) }
                    ?: notFoundException("Error", "Entity not found")
}