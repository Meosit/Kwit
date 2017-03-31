package by.mksn.kwitapi.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.sql.Timestamp
import javax.persistence.*

interface EntityIdSetable {
    fun setEntityId(id: Long?)
}

@Entity
@Table(name = "user", uniqueConstraints =
arrayOf(UniqueConstraint(columnNames = arrayOf("email"))))
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(columnDefinition = "INT")
        var id: Long?,
        @Column(length = 255, unique = true)
        var email: String,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @Column(columnDefinition = "CHAR(60)")
        var passwordHash: String,
        @Column(columnDefinition = "TINYINT")
        var salaryDay: Int?,
        @Column(columnDefinition = "DATETIME")
        val createdAt: Timestamp?,
        @Enumerated(EnumType.STRING)
        @Column(columnDefinition = "ENUM('USER', 'ADMIN')")
        val role: Role,
        var isDeleted: Boolean
) : EntityIdSetable, Serializable {
    override fun setEntityId(id: Long?) {
        this.id = id
    }

    enum class Role {
        USER, ADMIN
    }
}

@Entity
@Table(name = "currency")
data class Currency(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(columnDefinition = "INT")
        var id: Long?,
        @Column(columnDefinition = "CHAR(3)")
        val code: String,
        @Column(length = 5)
        val symbol: String,
        val isPrefix: Boolean
) : EntityIdSetable {
    override fun setEntityId(id: Long?) {
        this.id = id
    }
}

@Entity
@Table(name = "wallet")
data class Wallet(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(columnDefinition = "INT")
        var id: Long?,
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
) : EntityIdSetable {
    override fun setEntityId(id: Long?) {
        this.id = id
    }
}


@Entity
@Table(name = "category")
data class Category(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(columnDefinition = "INT")
        var id: Long?,
        @Column(columnDefinition = "INT")
        var userId: Long,
        @Column(length = 100)
        var name: String,
        var isIncome: Boolean
) : EntityIdSetable {
    override fun setEntityId(id: Long?) {
        this.id = id
    }
}


@Entity
@Table(name = "remittance")
data class Remittance(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(columnDefinition = "INT")
        var id: Long?,
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
) : EntityIdSetable {
    override fun setEntityId(id: Long?) {
        this.id = id
    }
}


@Entity
@Table(name = "transaction")
data class Transaction(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(columnDefinition = "INT")
        var id: Long? = null,
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
        var note: String?
) : EntityIdSetable {
    override fun setEntityId(id: Long?) {
        this.id = id
    }
}
