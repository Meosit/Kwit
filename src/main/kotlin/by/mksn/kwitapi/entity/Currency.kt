package by.mksn.kwitapi.entity

import by.mksn.kwitapi.entity.support.IdAssignable
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "currency")
data class Currency(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(columnDefinition = "INT")
        var id: Long? = null,
        @Size(min = 3, max = 3, message = "Currency code must have only 3 chars")
        @Column(columnDefinition = "CHAR(3)")
        val code: String,
        @Size(min = 1, max = 5, message = "Currency symbol length must be in range 1-5 chars")
        @Column(length = 5)
        val symbol: String,
        @NotNull
        @Column(name = "is_prefix")
        val isPrefix: Boolean
) : IdAssignable<Long> {
    override fun assignID(id: Long?) {
        this.id = id
    }
}