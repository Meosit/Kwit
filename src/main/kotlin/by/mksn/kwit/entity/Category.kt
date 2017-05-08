package by.mksn.kwit.entity

import by.mksn.kwit.entity.support.CategoryStats
import by.mksn.kwit.entity.support.CategoryType
import by.mksn.kwit.entity.support.UserBaseEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import java.math.BigDecimal
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@SqlResultSetMapping(name = "CategoryStatsMapping", classes = arrayOf(
        ConstructorResult(targetClass = CategoryStats::class, columns = arrayOf(
                ColumnResult(name = "categoryId", type = Long::class),
                ColumnResult(name = "categoryName", type = String::class),
                ColumnResult(name = "sumForCategory", type = BigDecimal::class),
                ColumnResult(name = "percentOfAll", type = BigDecimal::class),
                ColumnResult(name = "countForCategory", type = Int::class)
        ))
))
@NamedNativeQuery(name = "Category.fetchCategoryStats", query = """
SELECT
  categoryId,
  categoryName,
  IF(nullableSumForCategory IS NULL, 0, nullableSumForCategory)                  AS sumForCategory,
  (IF(nullableSumForCategory IS NULL, 0, nullableSumForCategory) / allSum) * 100 AS percentOfAll,
  IF(nullableCountForCategory IS NULL, 0, nullableCountForCategory)              AS countForCategory
FROM
  (SELECT
     category.id        AS categoryId,
     category.name      AS categoryName
   FROM category
     CROSS JOIN wallet
     JOIN currency
       ON wallet.currency_id = currency.id
   WHERE
     currency_id = :currencyId AND
     category.type = :categoryType AND
     wallet.user_id = :userId AND
     category.user_id = :userId
   GROUP BY categoryId
  ) AS first
  LEFT JOIN
  (SELECT
     transaction.category_id AS secondCategoryId,
     wallet.currency_id      AS secondCurrencyId,
     SUM(transaction.sum)    AS nullableSumForCategory,
     COUNT(transaction.id)   AS nullableCountForCategory
   FROM transaction
     JOIN wallet ON transaction.wallet_id = wallet.id
   WHERE
     currency_id = :currencyId AND
     wallet.user_id = :userId AND
     transaction.user_id = :userId
   GROUP BY secondCurrencyId, secondCategoryId
  ) AS second
    ON first.categoryId = secondCategoryId
  CROSS JOIN
  (SELECT
     SUM(transaction.sum)  AS allSum,
     COUNT(transaction.id) AS allCount
   FROM transaction
     JOIN category ON transaction.category_id = category.id
     JOIN wallet ON transaction.wallet_id = wallet.id
   WHERE
     wallet.user_id = :userId AND
     category.user_id = :userId AND
     transaction.user_id = :userId AND
     (transaction.date BETWEEN :startDate AND :endDate) AND
     wallet.currency_id = :currencyId AND
     category.type = :categoryType
  ) AS third
ORDER BY categoryId
""", resultSetMapping = "CategoryStatsMapping")
@Table(name = "category")
data class Category(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(columnDefinition = "INT")
        override var id: Long? = null,
        @get:JsonIgnore
        @Column(name = "user_id", columnDefinition = "INT")
        override var userId: Long? = null,
        @field:NotNull(message = "Name is not specified")
        @field:Size(min = 1, max = 100, message = "Name must be in range 1-100 symbols")
        @Column(length = 100)
        var name: String? = null,
        @field:NotNull(message = "Category type is not specified")
        @Enumerated(EnumType.STRING)
        @Column(columnDefinition = "ENUM('INCOME', 'OUTGO')")
        var type: CategoryType? = null
) : UserBaseEntity<Long>