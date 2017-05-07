package by.mksn.kwitapi.service

import by.mksn.kwitapi.entity.Wallet


interface WalletService : PersonalCrudService<Wallet, Long> {

    fun findAllByUserId(userId: Long): List<Wallet>

    fun softDelete(id: Long, newId: Long, userId: Long): Unit?

}
