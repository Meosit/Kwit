package by.mksn.kwitapi.controller

import by.mksn.kwitapi.configuration.security.UserAuth
import by.mksn.kwitapi.configuration.security.UserDetails
import by.mksn.kwitapi.entity.IdSetable
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import java.io.Serializable
import javax.validation.Valid

@RestController
interface PersonalCrudController<E : IdSetable<ID>, in ID : Serializable> {

    @PostMapping("")
    fun create(@Valid @RequestBody entity: E, @UserAuth auth: UserDetails): E

    @GetMapping("{id}")
    fun findById(@PathVariable("id") id: ID, @UserAuth auth: UserDetails): E

    @PutMapping("{id}")
    fun update(@PathVariable("id") id: ID, @Valid @RequestBody entity: E, @UserAuth auth: UserDetails): E

    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") id: ID, @UserAuth auth: UserDetails)

    @GetMapping("all")
    fun findAll(@UserAuth auth: UserDetails, pageable: Pageable): List<E>

}