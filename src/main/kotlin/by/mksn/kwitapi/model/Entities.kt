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
        @Column(length = 255, unique = true)
        var email: String,
        @Column(columnDefinition = "CHAR(60)")
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
        val id: Long,
        @Column(columnDefinition = "CHAR(3)")
        val code: String,
        @Column(length = 5)
        val symbol: String,
        val isPrefix: Boolean
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
        @Column(length = 100)
        var name: String,
        @Column(columnDefinition = "INT(11)")
        var balance: Long,
        var isSaving: Boolean,
        var isDeleted: Boolean
)

@Entity
@Table(name = "category")
data class Category(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(columnDefinition = "INT")
        var id: Long,
        @Column(columnDefinition = "INT")
        var userId: Long,
        @Column(length = 100)
        var name: String,
        var isIncome: Boolean
)

@Entity
@Table(name = "remittance")
data class Remittance(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(columnDefinition = "INT")
        var id: Long,
        @Column(columnDefinition = "INT")
        var userId: Long,
        @ManyToOne
        @JoinColumn(name = "wallet_donor_id")
        var walletDonor: Wallet,
        @ManyToOne
        @JoinColumn(name = "wallet_acceptor_id")
        var walletAcceptor: Wallet,
        @Column(columnDefinition = "INT(11)")
        var donorSum: Long,
        @Column(precision = 10, scale = 4)
        var conversion: Double,
        @Column(columnDefinition = "DATETIME")
        var date: Timestamp?
)

@Entity
@Table(name = "transaction")
data class Transaction(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(columnDefinition = "INT")
        var id: Long,
        @Column(columnDefinition = "INT")
        var userId: Long,
        @ManyToOne
        @JoinColumn(name = "wallet_id")
        var wallet: Wallet,
        @ManyToOne
        @JoinColumn(name = "category_id")
        var category: Category,
        @Column(columnDefinition = "INT(11)")
        var sum: Long,
        @Column(columnDefinition = "DATETIME")
        var date: Timestamp?,
        @Column(length = 300)
        var note: String
)