package by.mksn.kwitapi.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "wallet")
data class Wallet(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(columnDefinition = "INT")
        var id: Long? = null,
        @JsonIgnore
        @Column(name = "user_id", columnDefinition = "INT")
        var userId: Long,
        @ManyToOne
        @JoinColumn(name = "currency_id")
        var currency: Currency,
        @Column(length = 100)
        var name: String,
        @Column(columnDefinition = "INT(11)")
        var balance: Long,
        @Column(name = "is_saving")
        var isSaving: Boolean,
        @Column(name = "is_deleted")
        var isDeleted: Boolean = false
) : IdAndUserIdAssignable<Long> {
    override fun assignID(id: Long?) {
        this.id = id
    }

    override fun assignUserID(id: Long) {
        this.userId = id
    }
}