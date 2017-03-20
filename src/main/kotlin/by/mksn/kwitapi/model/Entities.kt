package by.mksn.kwitapi.model

import java.io.Serializable
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(columnDefinition = "INT")
        var id: Long,
        @Column(columnDefinition = "VARCHAR(255)", nullable = false)
        var email: String,
        @Column(columnDefinition = "CHAR(60)", nullable = false)
        var passwordHash: String,
        @Column(columnDefinition = "TINYINT")
        var salaryDay: Int?,
        @Column(columnDefinition = "DATETIME")
        var createdAt: Timestamp?,
        var isDeleted: Boolean
) : Serializable {
    private constructor() : this(0, "", "", null, null, false)
}