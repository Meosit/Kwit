package by.mksn.kwitapi.controller.impl

import by.mksn.kwitapi.configuration.security.Auth
import by.mksn.kwitapi.configuration.security.UserDetails
import by.mksn.kwitapi.controller.WalletController
import by.mksn.kwitapi.entity.Wallet
import by.mksn.kwitapi.entity.support.WalletType
import by.mksn.kwitapi.entity.support.WalletTypeBinder
import by.mksn.kwitapi.service.WalletService
import by.mksn.kwitapi.support.badRequestException
import by.mksn.kwitapi.support.wrapServiceCall
import org.slf4j.LoggerFactory
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

open class WalletControllerImpl(
        private val walletService: WalletService
) : AbstractPersonalCrudController<Wallet>(walletService, logger), WalletController {

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.registerCustomEditor(WalletType::class.java, WalletTypeBinder())
    }

    companion object {

        val logger = LoggerFactory.getLogger(WalletControllerImpl::class.java)!!
    }

    override fun softDelete(@PathVariable("id") id: Long?, @RequestParam("newWallet") newId: Long?, @Auth auth: UserDetails)
            = wrapServiceCall(logger) { walletService.softDelete(id!!, newId!!, auth.userId) }
            ?: badRequestException("Error", "Cannot delete category")

}
