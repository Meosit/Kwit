package by.mksn.kwit.service

import by.mksn.kwit.entity.Wallet
import by.mksn.kwit.entity.support.CostForecast


interface WalletService : PersonalCrudService<Wallet, Long> {

    fun findAllByUserId(userId: Long): List<Wallet>

    fun softDelete(id: Long, newId: Long, userId: Long): Unit?

    fun calculateCostForecast(userId: Long): CostForecast?

}
