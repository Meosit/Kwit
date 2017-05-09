package by.mksn.kwit.controller

import by.mksn.kwit.configuration.security.Auth
import by.mksn.kwit.configuration.security.UserDetails
import by.mksn.kwit.entity.Wallet
import by.mksn.kwit.entity.support.CostForecast
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/wallets")
interface WalletController : CrudController<Wallet, Long> {

    @DeleteMapping("{id}", params = arrayOf("newWallet"))
    fun softDelete(@PathVariable("id") id: Long?, @RequestParam("newWallet") newId: Long?, @Auth auth: UserDetails)

    @GetMapping("all")
    fun getAll(@Auth auth: UserDetails): List<Wallet>

    @GetMapping("forecast")
    fun getCostForecast(@Auth auth: UserDetails): CostForecast?

}