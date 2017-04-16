package by.mksn.kwitapi.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "category")
data class Category(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(columnDefinition = "INT")
        var id: Long? = null,
        @JsonIgnore
        @Column(name = "user_id", columnDefinition = "INT")
        var userId: Long,
        @Column(length = 100)
        var name: String,
        @Enumerated(EnumType.STRING)
        @Column(columnDefinition = "ENUM('INCOME', 'OUTGO')")
        var type: Type
) : IdAndUserIdAssignable<Long> {
    enum class Type {

        INCOME, OUTGO
    }

    override fun assignID(id: Long?) {
        this.id = id
    }

    override fun assignUserID(id: Long) {
        this.userId = id
    }
}