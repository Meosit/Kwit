package by.mksn.kwitapi.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "remittance")
data class Remittance(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(columnDefinition = "INT")
        var id: Long? = null,
        @JsonIgnore
        @Column(name = "user_id", columnDefinition = "INT")
        var userId: Long,
        @ManyToOne
        @JoinColumn(name = "wallet_donor_id")
        var walletDonor: Wallet,
        @ManyToOne
        @JoinColumn(name = "wallet_acceptor_id")
        var walletAcceptor: Wallet,
        @Column(name = "donor_sum", columnDefinition = "INT(11)")
        var donorSum: Long,
        @Column(precision = 10, scale = 4)
        var conversion: Double,
        @Column(columnDefinition = "DATETIME")
        var date: Timestamp? = null
) : IdAndUserIdAssignable<Long> {
    override fun assignID(id: Long?) {
        this.id = id
    }

    override fun assignUserID(id: Long) {
        this.userId = id
    }
}