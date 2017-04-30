package by.mksn.kwitapi.controller.impl

import by.mksn.kwitapi.configuration.security.Auth
import by.mksn.kwitapi.configuration.security.UserDetails
import by.mksn.kwitapi.controller.CategoryController
import by.mksn.kwitapi.entity.Category
import by.mksn.kwitapi.entity.support.CategoryStats
import by.mksn.kwitapi.entity.support.CategoryType
import by.mksn.kwitapi.entity.support.CategoryTypeBinder
import by.mksn.kwitapi.service.CategoryService
import by.mksn.kwitapi.support.till
import by.mksn.kwitapi.support.wrapServiceCall
import org.slf4j.LoggerFactory
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
            @PathVariable("currencyCode") currencyCode: String,
            @RequestParam(name = "from", required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") from: Date,
            @RequestParam(name = "to", required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") to: Date
    ): List<CategoryStats> =
            wrapServiceCall(logger) { categoryService.calculateCategoryStats(type, currencyCode, from till to) }

    override fun calculateCategoryStatsAllTime(
            @PathVariable("type") type: CategoryType,
            @PathVariable("currencyCode") currencyCode: String
    ): List<CategoryStats> =
            wrapServiceCall(logger) { categoryService.calculateCategoryStats(type, currencyCode, null) }

    override fun softDelete(@PathVariable("id") id: Long, @RequestParam("newCategory") newId: Long, @Auth auth: UserDetails) {
        logger.info("Mapping worked id: $id, newId: $newId")
    }

    override fun findAll(@Auth auth: UserDetails, @PathVariable("type") type: CategoryType, pageable: Pageable): List<Category>
            = wrapServiceCall(logger) { categoryService.findByUserIdAndType(auth.userId, type, pageable) }
}