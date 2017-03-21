package by.mksn.kwitapi.model

import java.io.Serializable
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "user", uniqueConstraints =
arrayOf(UniqueConstraint(columnNames = arrayOf("email"))))
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(columnDefinition = "INT")
        var id: Long,
        @Column(columnDefinition = "VARCHAR(255)", unique = true, nullable = false)
        var email: String,
        @Column(columnDefinition = "CHAR(60)", nullable = false)
        var passwordHash: String,
        @Column(columnDefinition = "TINYINT")
        var salaryDay: Int?,
        @Column(columnDefinition = "DATETIME")
        var createdAt: Timestamp?,
        var isDeleted: Boolean
) : Serializable

@Entity
@Table(name = "currency")
data class Currency(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(columnDefinition = "INT")
        var id: Long,
        @Column(columnDefinition = "CHAR(3)", nullable = false)
        var code: String,
        @Column(columnDefinition = "VARCHAR(5)", nullable = false)
        var symbol: String,
        var isPrefix: Boolean
)

@Entity
@Table(name = "wallet")
data class Wallet(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(columnDefinition = "INT")
        var id: Long,
        @Column(columnDefinition = "INT")
        var userId: Long,
        @ManyToOne
        @JoinColumn(name = "currency_id")
        var currency: Currency,
        var name: String
)