package by.mksn.kwitapi.controller

import by.mksn.kwitapi.configuration.security.Auth
import by.mksn.kwitapi.configuration.security.UserDetails
import by.mksn.kwitapi.entity.Wallet
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/wallets")
interface WalletController : CrudController<Wallet, Long> {

    @DeleteMapping("{id}", params = arrayOf("newWallet"))
    fun softDelete(@PathVariable("id") id: Long?, @RequestParam("newWallet") newId: Long?, @Auth auth: UserDetails)

}