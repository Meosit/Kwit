package by.mksn.kwitapi.controller

import by.mksn.kwitapi.entity.Wallet
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/wallets")
interface WalletController : CrudController<Wallet, Long> {
}