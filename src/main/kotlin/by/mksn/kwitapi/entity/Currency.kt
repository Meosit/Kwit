package by.mksn.kwitapi.entity

import javax.persistence.*

@Entity
@Table(name = "currency")
data class Currency(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(columnDefinition = "INT")
        var id: Long? = null,
        @Column(columnDefinition = "CHAR(3)")
        val code: String,
        @Column(length = 5)
        val symbol: String,
        val isPrefix: Boolean
) : IdSetable<Long> {
    override fun setID(id: Long?) {
        this.id = id
    }
}