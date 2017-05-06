package by.mksn.kwitapi.entity.support

import javax.validation.Constraint
import javax.validation.Payload
import javax.validation.constraints.Pattern
import kotlin.reflect.KClass

private const val ATOM = "[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]"
private const val DOMAIN = "($ATOM+(\\.$ATOM+)+)"
private const val IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]"

private const val PATTERN = "^$ATOM+(\\.$ATOM+)*@($DOMAIN|$IP_DOMAIN)$"

@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = arrayOf())
@Pattern(regexp = PATTERN, flags = arrayOf(Pattern.Flag.CASE_INSENSITIVE))
annotation class ValidEmail(
        val message: String = "Invalid email format",
        val groups: Array<KClass<*>> = arrayOf(),
        val payload: Array<KClass<out Payload>> = arrayOf())

