package by.mksn.kwitapi.entity

import by.mksn.kwitapi.entity.support.IdAndUserIdAssignable
import com.fasterxml.jackson.annotation.JsonIgnore
import java.math.BigDecimal
import java.sql.Timestamp
import javax.persistence.*
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Digits
import javax.validation.constraints.NotNull

@Entity
@Table(name = "remittance")
data class Remittance(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(columnDefinition = "INT")
        var id: Long? = null,
        @get:JsonIgnore
        @Column(name = "user_id", columnDefinition = "INT")
        var userId: Long? = null,
        @field:NotNull(message = "Donor wallet is not specified")
        @ManyToOne(targetEntity = Wallet::class)
        @JoinColumn(name = "wallet_donor_id")
        var walletDonor: Wallet?,
        @field:NotNull(message = "Acceptor wallet is not specified")
        @ManyToOne(targetEntity = Wallet::class)
        @JoinColumn(name = "wallet_acceptor_id")
        var walletAcceptor: Wallet?,
        @field:NotNull(message = "Donor sum is not specified")
        @field:DecimalMin("0.0000", message = "Donor sum must be positive")
        @field:Digits(integer = 19, fraction = 4, message = "Donor sum value is invalid")
        @Column(name = "donor_sum", columnDefinition = "INT(11)")
        var donorSum: BigDecimal? = null,
        @field:NotNull(message = "Conversion value is not specified")
        @field:Digits(integer = 10, fraction = 4, message = "Conversion value is invalid")
        @Column(precision = 10, scale = 4)
        var conversion: BigDecimal? = null,
        @field:NotNull(message = "Date is not specified")
        @Column(columnDefinition = "DATETIME")
        var date: Timestamp? = null
) : IdAndUserIdAssignable<Long> {
    override fun assignID(id: Long?) {
        this.id = id
    }

    override fun assignUserID(id: Long?) {
        this.userId = id
    }
}