package by.mksn.kwit.entity.support

import javax.validation.Constraint
import javax.validation.Payload
import javax.validation.constraints.Pattern
import kotlin.reflect.KClass

private const val PATTERN = "[A-Z]{3}"

@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = arrayOf())
@Pattern(regexp = PATTERN, flags = arrayOf(Pattern.Flag.CASE_INSENSITIVE))
annotation class CurrencyCode(
        val message: String = "Invalid currency code format",
        val groups: Array<KClass<*>> = arrayOf(),
        val payload: Array<KClass<out Payload>> = arrayOf())