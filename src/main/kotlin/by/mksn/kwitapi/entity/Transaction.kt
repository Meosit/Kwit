package by.mksn.kwitapi.entity

import by.mksn.kwitapi.entity.support.IdAndUserIdAssignable
import com.fasterxml.jackson.annotation.JsonIgnore
import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "transaction")
data class Transaction(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(columnDefinition = "INT")
        var id: Long? = null,
        @JsonIgnore
        @Column(name = "user_id", columnDefinition = "INT")
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
) : IdAndUserIdAssignable<Long> {
    override fun assignID(id: Long?) {
        this.id = id
    }

    override fun assignUserID(id: Long) {
        this.userId = id
    }
}