package by.mksn.kwit.entity

import by.mksn.kwit.entity.support.BaseEntity
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "currency")
data class Currency(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(columnDefinition = "INT")
        override var id: Long? = null,
        @field:NotNull(message = "Currency code is not specified")
        @field:Size(min = 3, max = 3, message = "Currency code must have only 3 chars")
        @Column(columnDefinition = "CHAR(3)")
        val code: String,
        @field:NotNull(message = "Currency symbol is not specified")
        @field:Size(min = 1, max = 5, message = "Currency symbol length must be in range 1-5 chars")
        @Column(length = 5)
        val symbol: String,
        @field:NotNull(message = "Prefix flag is not specified")
        @Column(name = "is_prefix")
        val isPrefix: Boolean
) : BaseEntity<Long>