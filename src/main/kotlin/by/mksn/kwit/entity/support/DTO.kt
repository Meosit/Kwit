package by.mksn.kwit.entity.support

import by.mksn.kwit.entity.Currency
import by.mksn.kwit.support.DateRange
import org.hibernate.validator.constraints.Range
import java.math.BigDecimal
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class RegistrationDetails(
        @field:ValidEmail(message = "Invalid email format")
        @field:NotNull(message = "Email is not specified")
        @field:Size(min = 5, max = 50, message = "Email must be in range 5-255 symbols")
        val email: String? = null,
        @field:NotNull(message = "Password is not specified")
        @field:Size(min = 5, max = 50, message = "Password must be in range 5-50 symbols")
        val password: String? = null,
        @field:NotNull(message = "Password is not specified")
        @field:Size(min = 5, max = 50, message = "Password must be in range 5-50 symbols")
        val passwordConfrimation: String? = null
)

data class SalaryInfo(
        @field:NotNull(message = "Salary day is not specified")
        @field:Range(min = 1, max = 31, message = "Salary day must be in range 1-31")
        val salaryDay: Int? = null,
        @field:CurrencyCode(message = "Invalid currency format")
        @field:NotNull(message = "Currency code is not specified")
        @field:Size(min = 3, max = 3, message = "Code must have 3-symbol length")
        val salaryCurrencyCode: String? = null
)

data class PasswordChangeDetails(
        @field:NotNull(message = "Password is not specified")
        @field:Size(min = 5, max = 50, message = "Password must be in range 5-50 symbols")
        val oldPassword: String? = null,
        @field:NotNull(message = "New Password is not specified")
        @field:Size(min = 5, max = 50, message = "New Password must be in range 5-50 symbols")
        val password: String? = null,
        @field:NotNull(message = "New Password confirmation is not specified")
        @field:Size(min = 5, max = 50, message = "New Password confirmation must be in range 5-50 symbols")
        val passwordConfirmation: String? = null
)

data class CostForecast(
        val dailySumTillSalary: BigDecimal,
        val actualCosts: BigDecimal,
        val daysTillSalary: Int
)

data class CategoryStats(
        var categoryId: Long? = null,
        var categoryName: String? = null,
        var sumForCategory: BigDecimal? = null,
        var percentOfAll: BigDecimal? = null,
        var countForCategory: Int? = null
)

data class CategoriesStats(
        val currency: Currency,
        val categoryType: CategoryType,
        val period: DateRange?,
        val categories: List<CategoryStats>
)