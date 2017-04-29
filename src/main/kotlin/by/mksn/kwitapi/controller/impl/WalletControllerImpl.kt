package by.mksn.kwitapi.controller.impl

import by.mksn.kwitapi.controller.WalletController
import by.mksn.kwitapi.entity.Wallet
import by.mksn.kwitapi.entity.support.WalletType
import by.mksn.kwitapi.entity.support.WalletTypeBinder
import by.mksn.kwitapi.service.WalletService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.InitBinder

open class WalletControllerImpl(
        walletService: WalletService
) : AbstractPersonalCrudController<Wallet>(walletService, logger), WalletController {

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.registerCustomEditor(WalletType::class.java, WalletTypeBinder())
    }

    companion object {
        val logger = LoggerFactory.getLogger(WalletControllerImpl::class.java)!!
    }
}