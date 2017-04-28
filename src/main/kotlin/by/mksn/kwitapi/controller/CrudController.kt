package by.mksn.kwitapi.controller

import by.mksn.kwitapi.configuration.security.Auth
import by.mksn.kwitapi.configuration.security.UserDetails
import by.mksn.kwitapi.entity.support.IdAssignable
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import java.io.Serializable
import javax.validation.Valid

@RestController
interface CrudController<E : IdAssignable<ID>, in ID : Serializable> {

    @PostMapping("")
    fun create(@Valid @RequestBody entity: E, @Auth auth: UserDetails): E

    @GetMapping("{id}")
    fun findById(@PathVariable("id") id: ID, @Auth auth: UserDetails): E

    @PutMapping("{id}")
    fun update(@PathVariable("id") id: ID, @Valid @RequestBody entity: E, @Auth auth: UserDetails): E

    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") id: ID, @Auth auth: UserDetails)

    @GetMapping("")
    fun findAll(@Auth auth: UserDetails, pageable: Pageable): List<E>

}