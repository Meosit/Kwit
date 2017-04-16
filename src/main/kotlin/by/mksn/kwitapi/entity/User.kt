package by.mksn.kwitapi.entity

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.sql.Timestamp
import javax.persistence.*

enum class UserRole {
    USER, ADMIN
}

@Entity
@Table(name = "user", uniqueConstraints =
arrayOf(UniqueConstraint(columnNames = arrayOf("email"))))
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(columnDefinition = "INT")
        var id: Long? = null,
        @Column(length = 255, unique = true)
        var email: String,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @Column(name = "password_hash", columnDefinition = "CHAR(60)")
        var passwordHash: String,
        @Column(name = "salary_day", columnDefinition = "TINYINT")
        var salaryDay: Int? = null,
        @Enumerated(EnumType.STRING)
        @Column(columnDefinition = "ENUM('USER', 'ADMIN')")
        val role: UserRole = UserRole.USER,
        @Column(name = "created_at", columnDefinition = "DATETIME")
        val createdAt: Timestamp? = null,
        @Column(name = "is_deleted")
        var isDeleted: Boolean = false
) : IdAssignable<Long>, Serializable {
    override fun assignID(id: Long?) {
        this.id = id
    }
}
