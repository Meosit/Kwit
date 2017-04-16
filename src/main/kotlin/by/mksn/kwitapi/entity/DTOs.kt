package by.mksn.kwitapi.entity

import org.hibernate.validator.constraints.Email
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class RegistrationDetails(
        @Email
        @NotNull
        @Size(min = 5, max = 255)
        val email: String,
        @NotNull
        @Size(min = 6, max = 30)
        val password: String,
        @Min(1)
        @Max(31)
        val salaryDay: Int?
)

data class PasswordChangeDetails(
        @Email
        @NotNull
        @Size(min = 5, max = 255)
        val email: String,
        @NotNull
        @Size(min = 6, max = 30)
        val password: String,
        @NotNull
        @Size(min = 6, max = 30)
        val newPassword: String
)

data class CategoryStats(
        val categoryId: Long,
        val currencyId: Long,
        val sumForCategory: Long,
        val transactionCountForCategory: Int
)