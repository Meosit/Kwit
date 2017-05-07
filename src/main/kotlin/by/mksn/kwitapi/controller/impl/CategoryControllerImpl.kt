package by.mksn.kwitapi.controller.impl

import by.mksn.kwitapi.configuration.security.Auth
import by.mksn.kwitapi.configuration.security.UserDetails
import by.mksn.kwitapi.controller.CategoryController
import by.mksn.kwitapi.entity.Category
import by.mksn.kwitapi.entity.support.CategoriesStats
import by.mksn.kwitapi.entity.support.CategoryType
import by.mksn.kwitapi.entity.support.CategoryTypeBinder
import by.mksn.kwitapi.entity.support.CurrencyCode
import by.mksn.kwitapi.service.CategoryService
import by.mksn.kwitapi.support.badRequestException
import by.mksn.kwitapi.support.till
import by.mksn.kwitapi.support.wrapServiceCall
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import java.util.*


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
            @RequestParam(name = "from") @DateTimeFormat(pattern = "dd.MM.yyyy") from: Date,
            @RequestParam(name = "to") @DateTimeFormat(pattern = "dd.MM.yyyy") to: Date,
            @Auth auth: UserDetails
    ): CategoriesStats =
            wrapServiceCall(logger) { categoryService.calculateCategoryStats(auth.userId, type, currencyCode, from till to) }

    override fun calculateCategoryStatsAllTime(
            @PathVariable("type") type: CategoryType,
            @PathVariable("currencyCode") @CurrencyCode currencyCode: String,
            @Auth auth: UserDetails
    ): CategoriesStats =
            wrapServiceCall(logger) { categoryService.calculateCategoryStats(auth.userId, type, currencyCode, null) }

    override fun softDelete(@PathVariable("id") id: Long?, @RequestParam("newCategory") newId: Long?, @Auth auth: UserDetails)
            = wrapServiceCall(logger) { categoryService.softDelete(id!!, newId!!, auth.userId) }
            ?: badRequestException("Error", "Cannot delete category")

    override fun findAll(@Auth auth: UserDetails, @PathVariable("type") type: CategoryType, pageable: Pageable): Page<Category>
            = wrapServiceCall(logger) { categoryService.findByUserIdAndType(auth.userId, type, pageable) }
}