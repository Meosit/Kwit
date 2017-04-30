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
        override var id: Long? = null,
        @get:JsonIgnore
        @Column(name = "user_id", columnDefinition = "INT")
        override var userId: Long? = null,
        @field:NotNull(message = "Wallet is not specified")
        @ManyToOne
        @JoinColumn(name = "wallet_id")
        var wallet: Wallet?,
        @field:NotNull(message = "Category is not specified")
        @ManyToOne
        @JoinColumn(name = "category_id")
        var category: Category?,
        @field:NotNull(message = "Sum is not specified")
        @field:Digits(integer = 19, fraction = 4, message = "Invalid sum value")
        @Column(columnDefinition = "INT(11)")
        var sum: BigDecimal? = null,
        @field:NotNull(message = "Date is not specified")
        @Column(columnDefinition = "DATETIME")
        var date: Timestamp? = null,
        @field:Size(min = 1, max = 300, message = "Name must be in range 1-300 symbols")
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