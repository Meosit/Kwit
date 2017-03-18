package by.mksn.kwitapi.entity

import java.io.Serializable
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "user")
open class User(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(columnDefinition = "INT")
        var id: Long = 0,
        @Column(columnDefinition = "VARCHAR(255)", nullable = false)
        var email: String = "",
        @Column(columnDefinition = "CHAR(60)", nullable = false)
        var passwordHash: String = "",
        @Column(columnDefinition = "TINYINT")
        var salaryDay: Int? = null,
        @Column(columnDefinition = "DATETIME")
        var createdAt: Timestamp? = null,
        var isDeleted: Boolean = false
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (id != other.id) return false
        if (email != other.email) return false
        if (passwordHash != other.passwordHash) return false
        if (salaryDay != other.salaryDay) return false
        if (createdAt != other.createdAt) return false
        if (isDeleted != other.isDeleted) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + passwordHash.hashCode()
        result = 31 * result + (salaryDay ?: 0)
        result = 31 * result + (createdAt?.hashCode() ?: 0)
        result = 31 * result + isDeleted.hashCode()
        return result
    }

    override fun toString(): String {
        return "User(id=$id, " +
                "email='$email', " +
                "passwordHash='$passwordHash', " +
                "salaryDay=$salaryDay, " +
                "createdAt=$createdAt, " +
                "isDeleted=$isDeleted)"
    }


}