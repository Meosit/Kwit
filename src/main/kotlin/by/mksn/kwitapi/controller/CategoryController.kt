package by.mksn.kwitapi.controller

import by.mksn.kwitapi.configuration.security.UserAuth
import by.mksn.kwitapi.configuration.security.UserDetails
import by.mksn.kwitapi.entity.Category
import by.mksn.kwitapi.entity.CategoryStats
import org.springframework.data.domain.Pageable
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.sql.Timestamp

@RestController
@RequestMapping("/category")
interface CategoryController : PersonalCrudController<Category, Long> {

    @GetMapping("all/{type}")
    fun findAll(
            @UserAuth auth: UserDetails,
            @PathVariable("type") type: Category.Type,
            pageable: Pageable
    ): List<Category>

    @GetMapping("stats/{type}/{currencyId}")
    fun calculateCategoryStats(
            @PathVariable("type") type: Category.Type,
            @PathVariable("currencyId") currencyId: Long,
            @RequestParam("start") @DateTimeFormat(pattern = "dd.MM.yyyy") startDate: Timestamp,
            @RequestParam("end") @DateTimeFormat(pattern = "dd.MM.yyyy") endDate: Timestamp
    ): List<CategoryStats>

}