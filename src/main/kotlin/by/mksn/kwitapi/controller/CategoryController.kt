package by.mksn.kwitapi.controller

import by.mksn.kwitapi.configuration.security.UserAuth
import by.mksn.kwitapi.entity.Category
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("category")
interface CategoryController : PersonalCrudController<Category, Long> {

    @GetMapping("all/{type}")
    fun findAll(
            @UserAuth auth: UserAuth,
            @PathVariable("type") type: Category.Type,
            pageable: Pageable
    ): List<Category>

}