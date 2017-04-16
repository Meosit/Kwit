package by.mksn.kwitapi.service.impl

import by.mksn.kwitapi.entity.Wallet
import by.mksn.kwitapi.repository.TransactionRepository
import by.mksn.kwitapi.repository.WalletRepository
import by.mksn.kwitapi.service.WalletService
import by.mksn.kwitapi.wrapJPACall
import org.springframework.data.domain.Pageable

open class WalletServiceImpl(
        private val walletRepository: WalletRepository, val transactionRepository: TransactionRepository
) : AbstractCrudService<Wallet>(walletRepository), WalletService {

    override fun findAllByUserId(userId: Long, pageable: Pageable): List<Wallet> = wrapJPACall {
        walletRepository.findByUserIdOrderByIsSavingAsc(userId, pageable)
    }
}