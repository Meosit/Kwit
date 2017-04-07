package by.mksn.kwitapi.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "remittance")
data class Remittance(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(columnDefinition = "INT")
        var id: Long? = null,
        @JsonIgnore
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
        var date: Timestamp? = null
) : IdSetable<Long> {
    override fun setID(id: Long?) {
        this.id = id
    }
}