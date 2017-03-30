package by.mksn.kwitapi.controller

import by.mksn.kwitapi.model.Currency
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotEmpty
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

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

data class RegistrationDetails(
        @Email
        @NotNull(message = "Email must be not null")
        @NotEmpty(message = "Email must be not empty")
        @Size(min = 5, max = 255, message = "Email must be between 5 and 255 characters")
        val email: String,
        @NotNull(message = "Password must be not null")
        @NotEmpty(message = "Password must be not empty")
        @Size(min = 6, max = 30, message = "Password must be between 6 and 20 characters")
        val password: String,
        @Min(value = 1, message = "Salary Day must be between 1 and 31")
        @Max(value = 31, message = "Salary Day must be between 1 and 31")
        val salaryDat: Int?
)

data class PasswordChangeDetails(
        @Email
        @NotNull(message = "Email must be not null")
        @NotEmpty(message = "Email must be not empty")
        @Size(min = 5, max = 255, message = "Email must be between 5 and 255 characters")
        val email: String,
        @NotNull(message = "Password must be not null")
        @NotEmpty(message = "Password must be not empty")
        @Size(min = 6, max = 30, message = "password must be between 6 and 20 characters")
        val password: String,
        @NotNull(message = "New password must be not null")
        @NotEmpty(message = "New password must be not empty")
        @Size(min = 6, max = 30, message = "New password must be between 6 and 20 characters")
        val newPassword: String
)
