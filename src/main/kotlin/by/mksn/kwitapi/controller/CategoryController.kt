package by.mksn.kwitapi.controller

import by.mksn.kwitapi.configuration.security.Auth
import by.mksn.kwitapi.configuration.security.UserDetails
import by.mksn.kwitapi.entity.Category
import by.mksn.kwitapi.entity.support.CategoryStats
import by.mksn.kwitapi.entity.support.CategoryType
import org.springframework.data.domain.Pageable
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/categories")
interface CategoryController : CrudController<Category, Long> {

    @GetMapping("type/{type}")
    fun findAll(
            @Auth auth: UserDetails,
            @PathVariable("type") type: CategoryType,
            pageable: Pageable
    ): List<Category>

    @GetMapping("stats/{type}/{currencyCode}", params = arrayOf("from", "to"))
    fun calculateCategoryStats(
            @PathVariable("type") type: CategoryType,
            @PathVariable("currencyCode") currencyCode: String,
            @RequestParam(name = "from") @DateTimeFormat(pattern = "dd.MM.yyyy") from: Date,
            @RequestParam(name = "to") @DateTimeFormat(pattern = "dd.MM.yyyy") to: Date
    ): List<CategoryStats>

    @GetMapping("stats/{type}/{currencyCode}")
    fun calculateCategoryStatsAllTime(
            @PathVariable("type") type: CategoryType,
            @PathVariable("currencyCode") currencyCode: String
    ): List<CategoryStats>

    @DeleteMapping("{id}", params = arrayOf("newCategory"))
    fun softDelete(@PathVariable("id") id: Long, @RequestParam("newCategory") newId: Long, @Auth auth: UserDetails)

}