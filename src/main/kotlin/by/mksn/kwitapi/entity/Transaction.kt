package by.mksn.kwitapi.entity

import by.mksn.kwitapi.entity.support.IdAndUserIdAssignable
import com.fasterxml.jackson.annotation.JsonIgnore
import java.math.BigDecimal
import java.sql.Timestamp
import javax.persistence.*
import javax.validation.constraints.Digits
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "transaction")
data class Transaction(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(columnDefinition = "INT")
        var id: Long? = null,
        @JsonIgnore
        @Column(name = "user_id", columnDefinition = "INT")
        var userId: Long? = null,
        @NotNull(message = "Wallet is not specified")
        @ManyToOne
        @JoinColumn(name = "wallet_id")
        var wallet: Wallet,
        @NotNull(message = "Category is not specified")
        @ManyToOne
        @JoinColumn(name = "category_id")
        var category: Category,
        @NotNull(message = "Sum is not specified")
        @Digits(integer = 19, fraction = 4, message = "Invalid sum value")
        @Column(columnDefinition = "INT(11)")
        var sum: BigDecimal? = null,
        @NotNull(message = "Date is not specified")
        @Column(columnDefinition = "DATETIME")
        var date: Timestamp? = null,
        @Size(min = 1, max = 300, message = "Name must be in range 1-300 symbols")
        @Column(length = 300)
        var note: String?
) : IdAndUserIdAssignable<Long> {
    override fun assignID(id: Long?) {
        this.id = id
    }

    override fun assignUserID(id: Long?) {
        this.userId = id
    }
}