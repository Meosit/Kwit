@file:Suppress("SpringKotlinAutowiring")

package by.mksn.kwit.configuration

import by.mksn.kwit.controller.*
import by.mksn.kwit.controller.exception.WhiteLabelErrorPageController
import by.mksn.kwit.controller.impl.*
import by.mksn.kwit.service.*
import org.springframework.boot.autoconfigure.web.ErrorController
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ControllerConfiguration {

    @Bean
    fun errorController(): ErrorController
            = WhiteLabelErrorPageController()

    @Bean
    fun currencyController(currencyService: CurrencyService): CurrencyController
            = CurrencyControllerImpl(currencyService)

    @Bean
    fun walletController(walletService: WalletService): WalletController
            = WalletControllerImpl(walletService)

    @Bean
    fun categoryController(categoryService: CategoryService): CategoryController
            = CategoryControllerImpl(categoryService)

    @Bean
    fun transactionController(transactionService: TransactionService): TransactionController
            = TransactionControllerImpl(transactionService)

    @Bean
    fun remittanceController(remittanceService: RemittanceService): RemittanceController
            = RemittanceControllerImpl(remittanceService)

    @Bean
    fun userController(userService: UserService): UserController
            = UserControllerImpl(userService)
}
