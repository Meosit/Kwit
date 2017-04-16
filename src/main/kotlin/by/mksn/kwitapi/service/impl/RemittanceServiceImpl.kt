package by.mksn.kwitapi.service.impl

import by.mksn.kwitapi.entity.Remittance
import by.mksn.kwitapi.repository.RemittanceRepository
import by.mksn.kwitapi.repository.WalletRepository
import by.mksn.kwitapi.service.RemittanceService
import by.mksn.kwitapi.wrapJPACall
import org.springframework.data.domain.Pageable

open class RemittanceServiceImpl(
        private val remittanceRepository: RemittanceRepository,
        private val walletRepository: WalletRepository
) : AbstractCrudService<Remittance>(remittanceRepository), RemittanceService {

    override fun findAllByUserId(userId: Long, pageable: Pageable): List<Remittance> = wrapJPACall {
        remittanceRepository.findByUserIdOrderByDateDescIdDesc(userId, pageable)
    }

}