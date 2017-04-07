package by.mksn.kwitapi.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "transaction")
data class Transaction(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(columnDefinition = "INT")
        var id: Long? = null,
        @JsonIgnore
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
        var date: Timestamp? = null,
        @Column(length = 300)
        var note: String?
) : IdSetable<Long> {
    override fun setID(id: Long?) {
        this.id = id
    }
}