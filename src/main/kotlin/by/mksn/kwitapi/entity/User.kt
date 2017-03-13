package by.mksn.kwitapi.entity

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "user")
open class User(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(columnDefinition = "INTEGER")
        var id: Long = 0,
        @Column(columnDefinition = "VARCHAR(60)", nullable = false)
        var username: String = "",
        @Column(columnDefinition = "VARCHAR(255)", nullable = false)
        var email: String = "",
        @Column(columnDefinition = "CHAR(60)", nullable = false)
        var passwordHash: String = "",
        @Column(columnDefinition = "TINYINT", nullable = true)
        var salaryDay: Int? = null,
        var isDeleted: Boolean = false,
        @Column(columnDefinition = "DATETIME", nullable = true)
        var createdAt: Timestamp? = null
)