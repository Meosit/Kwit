package by.mksn.kwitapi.service

import by.mksn.kwitapi.entity.Remittance
import org.springframework.data.domain.Pageable


interface RemittanceService : CrudService<Remittance, Long> {
    fun findByUserId(id: Long, pageable: Pageable): List<Remittance>
}