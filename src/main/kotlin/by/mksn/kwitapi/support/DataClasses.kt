package by.mksn.kwitapi.support

import org.springframework.http.HttpStatus
import java.sql.Timestamp

data class TimestampRange(
        val start: Timestamp,
        val end: Timestamp
)

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
