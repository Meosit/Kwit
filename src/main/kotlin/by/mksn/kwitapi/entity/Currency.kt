package by.mksn.kwitapi.entity

import javax.persistence.*

@Entity
@Table(name = "currency")
data class Currency(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(columnDefinition = "INT")
        var id: Long? = null,
        @Column(columnDefinition = "CHAR(3)")
        val code: String,
        @Column(length = 5)
        val symbol: String,
        @Column(name = "is_prefix")
        val isPrefix: Boolean
) : IdAssignable<Long> {
    override fun assignID(id: Long?) {
        this.id = id
    }
}