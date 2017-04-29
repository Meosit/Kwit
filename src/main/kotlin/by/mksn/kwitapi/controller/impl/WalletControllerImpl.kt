package by.mksn.kwitapi.controller.impl

import by.mksn.kwitapi.controller.WalletController
import by.mksn.kwitapi.entity.Wallet
import by.mksn.kwitapi.service.WalletService
import org.slf4j.LoggerFactory

open class WalletControllerImpl(
        walletService: WalletService
) : AbstractPersonalCrudController<Wallet>(walletService, logger), WalletController {

    companion object {
        val logger = LoggerFactory.getLogger(WalletControllerImpl::class.java)!!
    }
}