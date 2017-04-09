package by.mksn.kwitapi.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "category")
data class Category(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(columnDefinition = "INT")
        var id: Long? = null,
        @JsonIgnore
        @Column(columnDefinition = "INT")
        var userId: Long,
        @Column(length = 100)
        var name: String,
        var type: Type
) : IdSetable<Long> {

    enum class Type {
        INCOME, OUTGO
    }

    override fun setID(id: Long?) {
        this.id = id
    }
}