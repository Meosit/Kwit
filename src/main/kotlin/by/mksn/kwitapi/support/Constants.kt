package by.mksn.kwitapi.support

import java.sql.Timestamp

const val DEFAULT_ENCODING = "UTF-8"
const val DEFAULT_BCRYPT_STRENGTH = 12

const val REFRESH_TOKEN_VALIDITY_SECONDS = 86400
const val ACCESS_TOKEN_VALIDITY_SECONDS = 3600

val MAX_SQL_DATETIME = Timestamp.valueOf("9999-12-31 23:59:59")
val MIN_SQL_DATETIME = Timestamp.valueOf("1000-01-01 00:00:00")