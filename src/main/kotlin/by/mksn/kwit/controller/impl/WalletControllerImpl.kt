package by.mksn.kwit.controller.impl

import by.mksn.kwit.configuration.security.Auth
import by.mksn.kwit.configuration.security.UserDetails
import by.mksn.kwit.controller.WalletController
import by.mksn.kwit.entity.Wallet
import by.mksn.kwit.entity.support.WalletType
import by.mksn.kwit.entity.support.WalletTypeBinder
import by.mksn.kwit.service.WalletService
import by.mksn.kwit.support.badRequestException
import by.mksn.kwit.support.wrapServiceCall
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

    override fun findAll(@Auth auth: UserDetails): List<Wallet> =
            wrapServiceCall(logger) { walletService.findAllByUserId(auth.userId) }

    override fun softDelete(@PathVariable("id") id: Long?, @RequestParam("newWallet") newId: Long?, @Auth auth: UserDetails) =
            wrapServiceCall(logger) { walletService.softDelete(id!!, newId!!, auth.userId) }
            ?: badRequestException("Error", "Cannot delete category")

}
