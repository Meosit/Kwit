package by.mksn.kwitapi.entity

import by.mksn.kwitapi.entity.support.CategoryType
import by.mksn.kwitapi.entity.support.IdAndUserIdAssignable
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "category")
data class Category(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(columnDefinition = "INT")
        var id: Long? = null,
        @JsonIgnore
        @Column(name = "user_id", columnDefinition = "INT")
        var userId: Long? = null,
        @NotNull(message = "Name is not specified")
        @Size(min = 1, max = 100, message = "Name must be in range 1-100 symbols")
        @Column(length = 100)
        var name: String? = null,
        @NotNull(message = "Category type is not specified")
        @Enumerated(EnumType.STRING)
        @Column(columnDefinition = "ENUM('INCOME', 'OUTGO')")
        var type: CategoryType? = null
) : IdAndUserIdAssignable<Long> {

    override fun assignID(id: Long?) {
        this.id = id
    }

    override fun assignUserID(id: Long?) {
        this.userId = id
    }
}