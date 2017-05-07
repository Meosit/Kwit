package by.mksn.kwit.entity

import by.mksn.kwit.entity.support.UserBaseEntity
import by.mksn.kwit.entity.support.WalletType
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
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
        override var id: Long? = null,
        @get:JsonIgnore
        @Column(name = "user_id", columnDefinition = "INT")
        override var userId: Long?,
        @field:NotNull(message = "Currency is not specified")
        @ManyToOne(fetch = FetchType.EAGER)
        @Fetch(FetchMode.JOIN)
        @JoinColumn(name = "currency_id")
        var currency: Currency?,
        @field:NotNull(message = "Name is not specified")
        @field:Size(min = 1, max = 100, message = "Name must be in range 1-100 symbols")
        @Column(length = 100)
        var name: String?,
        @field:NotNull(message = "Balance is not specified")
        @field:Digits(integer = 19, fraction = 4, message = "Balance value is invalid")
        @Column(columnDefinition = "INT(11)")
        var balance: BigDecimal?,
        @field:NotNull(message = "Type is not specified")
        @Enumerated(EnumType.STRING)
        @Column(columnDefinition = "ENUM('NORMAL', 'SAVING')")
        var type: WalletType?
) : UserBaseEntity<Long>