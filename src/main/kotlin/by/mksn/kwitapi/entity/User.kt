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
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(columnDefinition = "INT")
        var id: Long? = null,
        @Column(length = 255, unique = true)
        var email: String,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @Column(columnDefinition = "CHAR(60)")
        var passwordHash: String,
        @Column(columnDefinition = "TINYINT")
        var salaryDay: Int? = null,
        @Column(columnDefinition = "DATETIME")
        val createdAt: Timestamp? = null,
        @Enumerated(EnumType.STRING)
        @Column(columnDefinition = "ENUM('USER', 'ADMIN')")
        val role: UserRole = UserRole.USER,
        var isDeleted: Boolean = false
) : IdSetable<Long>, Serializable {
    override fun setID(id: Long?) {
        this.id = id
    }
}
