package by.mksn.kwitapi.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "wallet")
data class Wallet(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(columnDefinition = "INT")
        var id: Long? = null,
        @JsonIgnore
        @Column(columnDefinition = "INT")
        var userId: Long,
        @ManyToOne
        @JoinColumn(name = "currency_id")
        var currency: Currency,
        @Column(length = 100)
        var name: String,
        @Column(columnDefinition = "INT(11)")
        var balance: Long,
        var isSaving: Boolean,
        var isDeleted: Boolean = false
) : IdSetable<Long> {
    override fun setID(id: Long?) {
        this.id = id
    }
}