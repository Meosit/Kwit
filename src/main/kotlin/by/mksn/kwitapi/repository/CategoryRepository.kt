package by.mksn.kwitapi.repository

import by.mksn.kwitapi.entity.Category
import by.mksn.kwitapi.entity.support.CategoryStats
import by.mksn.kwitapi.entity.support.CategoryType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.sql.Timestamp

@Repository
interface CategoryRepository :
        PagingAndSortingRepository<Category, Long>,
        PersonalCrudRepository<Category, Long> {

    fun findByUserIdOrderByIdAsc(id: Long, pageable: Pageable): Page<Category>

    fun findByUserIdAndType(id: Long, type: CategoryType, pageable: Pageable): Page<Category>

    fun findCategoryStats(
            @Param("userId") userId: Long,
            @Param("currencyId") currencyId: Long,
            @Param("categoryType") categoryType: CategoryType,
            @Param("startDate") startDate: Timestamp,
            @Param("endDate") endDate: Timestamp
    ): List<CategoryStats>
}