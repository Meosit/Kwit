package by.mksn.kwit.entity

import by.mksn.kwit.entity.support.BaseEntity
import by.mksn.kwit.entity.support.UserRole
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.Range
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "user", uniqueConstraints =
arrayOf(UniqueConstraint(columnNames = arrayOf("email"))))
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(columnDefinition = "INT")
        override var id: Long? = null,
        @field:NotNull(message = "Email is not specified")
        @field:Size(min = 1, max = 100, message = "Email must be in range 1-255 symbols")
        @Column(length = 255, unique = true)
        var email: String?,
        @field:NotNull(message = "Password hash is not specified")
        @field:Size(min = 60, max = 60, message = "Hash must be in range 1-100 symbols")
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @Column(name = "password_hash", columnDefinition = "CHAR(60)")
        var passwordHash: String?,
        @field:Range(min = 1, max = 31, message = "Salary day must be in range 1-31")
        @Column(name = "salary_day", columnDefinition = "TINYINT")
        var salaryDay: Int? = null,
        @field:NotNull(message = "User role is not specified")
        @Enumerated(EnumType.STRING)
        @Column(columnDefinition = "ENUM('USER', 'ADMIN')")
        val role: UserRole? = UserRole.USER,
        @get:JsonIgnore
        @Column(name = "created_at", columnDefinition = "DATETIME")
        val createdAt: Date? = null,
        @get:JsonIgnore
        @Column(name = "is_deleted")
        var isDeleted: Boolean = false
) : BaseEntity<Long>, Serializable