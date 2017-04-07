package by.mksn.kwitapi.controller

import by.mksn.kwitapi.configuration.security.UserAuth
import by.mksn.kwitapi.entity.IdSetable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
interface CrudController<E : IdSetable<ID>, in ID> {

    @PostMapping("")
    fun create(@Valid @RequestBody entity: E, @UserAuth auth: UserAuth): E

    @GetMapping("{id}")
    fun findById(@PathVariable("id") id: ID, @UserAuth auth: UserAuth): E

    @PutMapping("{id}")
    fun update(@PathVariable("id") id: ID, @Valid @RequestBody entity: E, @UserAuth auth: UserAuth): E

    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") id: ID, @UserAuth auth: UserAuth)
}