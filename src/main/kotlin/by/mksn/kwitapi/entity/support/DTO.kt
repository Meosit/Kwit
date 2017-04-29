package by.mksn.kwitapi.entity.support

import org.hibernate.validator.constraints.Range
import java.math.BigDecimal
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class RegistrationDetails(
        @field:ValidEmail(message = "Invalid email format")
        @field:NotNull(message = "Email is not specified")
        @field:Size(min = 5, max = 50, message = "Email must be in range 5-255 symbols")
        val email: String,
        @field:NotNull(message = "Password is not specified")
        @field:Size(min = 5, max = 50, message = "Password must be in range 5-50 symbols")
        val password: String,
        @field:Range(min = 1, max = 31, message = "Salary day must be in range 1-31")
        val salaryDay: Int?
)

data class PasswordChangeDetails(
        @field:ValidEmail(message = "Invalid email format")
        @field:NotNull(message = "Email is not specified")
        @field:Size(min = 5, max = 50, message = "Email must be in range 5-255 symbols")
        val email: String,
        @field:NotNull(message = "Password is not specified")
        @field:Size(min = 5, max = 50, message = "Password must be in range 5-50 symbols")
        val password: String,
        @field:NotNull(message = "New Password is not specified")
        @field:Size(min = 5, max = 50, message = "New Password must be in range 5-50 symbols")
        val newPassword: String
)

data class CategoryStats(
        val categoryId: Long,
        val categoryName: String,
        val currencyCode: String,
        val currencySymbol: String,
        val currencyIsPrefix: Boolean,
        val sumForCategory: BigDecimal,
        val percentOfAll: BigDecimal,
        val transactionCountForCategory: Int
)