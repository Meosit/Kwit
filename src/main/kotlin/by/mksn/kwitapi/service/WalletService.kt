package by.mksn.kwitapi.service

import by.mksn.kwitapi.entity.Wallet
import org.springframework.data.domain.Pageable


interface WalletService : CrudService<Wallet, Long> {
    fun findByUserId(id: Long, pageable: Pageable): List<Wallet>
}
