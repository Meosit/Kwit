package by.mksn.kwit.controller

import by.mksn.kwit.configuration.security.Auth
import by.mksn.kwit.configuration.security.UserDetails
import by.mksn.kwit.entity.Category
import by.mksn.kwit.entity.support.CategoriesStats
import by.mksn.kwit.entity.support.CategoryType
import by.mksn.kwit.entity.support.CurrencyCode
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/categories")
interface CategoryController : CrudController<Category, Long> {

    @GetMapping("type/{type}")
    fun findAll(
            @Auth auth: UserDetails,
            @PathVariable("type") type: CategoryType
    ): List<Category>

    @GetMapping("all")
    fun findAll(@Auth auth: UserDetails): List<Category>

    @GetMapping("stats/{type}/{currencyCode}", params = arrayOf("from", "to"))
    fun calculateCategoryStats(
            @PathVariable("type") type: CategoryType,
            @PathVariable("currencyCode") @CurrencyCode currencyCode: String,
            @RequestParam(name = "from") @DateTimeFormat(pattern = "dd.MM.yyyy") from: Date,
            @RequestParam(name = "to") @DateTimeFormat(pattern = "dd.MM.yyyy") to: Date,
            @Auth auth: UserDetails
    ): CategoriesStats

    @GetMapping("stats/{type}/{currencyCode}")
    fun calculateCategoryStatsAllTime(
            @PathVariable("type") type: CategoryType,
            @PathVariable("currencyCode") @CurrencyCode currencyCode: String,
            @Auth auth: UserDetails
    ): CategoriesStats

    @DeleteMapping("{id}", params = arrayOf("newCategory"))
    fun softDelete(@PathVariable("id") id: Long?, @RequestParam("newCategory") newId: Long?, @Auth auth: UserDetails)

}