package by.mksn.kwitapi.controller

import by.mksn.kwitapi.model.Currency
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/oauth")
interface OAuthController {

        @PutMapping("/register")
        fun register(@Valid @RequestBody registrationDetails: RegistrationDetails)

        @GetMapping("/revoke-token")
        fun logout(request: HttpServletRequest)

        @PostMapping("/change-password")
        fun changePassword(@Valid @RequestBody passwordChangeDetails: PasswordChangeDetails)

}

@RestController
@RequestMapping("/currency")
interface CurrencyController : CrudController<Currency, Long> {

        @RequestMapping("/all")
        fun findAll(): List<Currency>

        @RequestMapping("/{code}")
        fun findByCode(@PathVariable("code") code: String): Currency

}