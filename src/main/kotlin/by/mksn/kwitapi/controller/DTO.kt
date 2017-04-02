package by.mksn.kwitapi.controller

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotEmpty
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

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
        val salaryDay: Int?
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
