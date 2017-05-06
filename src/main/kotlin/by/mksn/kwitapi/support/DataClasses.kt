package by.mksn.kwitapi.support

import org.springframework.http.HttpStatus
import java.util.*

data class DateRange(
        val start: Date,
        val end: Date
) {
    companion object {
        val NONE = DateRange(MIN_SQL_DATETIME, MAX_SQL_DATETIME)
    }
}

data class RestError(
        val status: Int,
        val error: String,
        val errors: List<RestErrorMessage>
) {
    constructor(status: HttpStatus, vararg errors: RestErrorMessage) : this(
            status.value(),
            status.reasonPhrase,
            errors.asList())

    constructor(status: HttpStatus, errors: List<RestErrorMessage>) : this(
            status.value(),
            status.reasonPhrase,
            errors)

}

data class RestErrorMessage(
        val title: String,
        val message: String
)
