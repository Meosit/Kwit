package by.mksn.kwitapi.entity

import by.mksn.kwitapi.entity.support.IdAndUserIdAssignable
import by.mksn.kwitapi.entity.support.WalletType
import com.fasterxml.jackson.annotation.JsonIgnore
import java.math.BigDecimal
import javax.persistence.*
import javax.validation.constraints.Digits
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "wallet")
data class Wallet(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(columnDefinition = "INT")
        var id: Long? = null,
        @JsonIgnore
        @Column(name = "user_id", columnDefinition = "INT")
        var userId: Long?,
        @NotNull(message = "Currency is not specified")
        @ManyToOne
        @JoinColumn(name = "currency_id")
        var currency: Currency?,
        @NotNull(message = "Name is not specified")
        @Size(min = 1, max = 100, message = "Name must be in range 1-100 symbols")
        @Column(length = 100)
        var name: String,
        @NotNull(message = "Balance is not specified")
        @Digits(integer = 19, fraction = 4, message = "Balance value is invalid")
        @Column(columnDefinition = "INT(11)")
        var balance: BigDecimal,
        @NotNull(message = "Type is not specified")
        @Column(name = "type")
        var type: WalletType?,
        @JsonIgnore
        @Column(name = "is_deleted")
        var isDeleted: Boolean? = false
) : IdAndUserIdAssignable<Long> {
    override fun assignID(id: Long?) {
        this.id = id
    }

    override fun assignUserID(id: Long?) {
        this.userId = id
    }
}