@file:Suppress("SpringKotlinAutowiring")

package by.mksn.kwitapi.configuration

import by.mksn.kwitapi.controller.*
import by.mksn.kwitapi.controller.exception.WhiteLabelErrorPageController
import by.mksn.kwitapi.controller.impl.*
import by.mksn.kwitapi.service.*
import org.springframework.boot.autoconfigure.web.ErrorController
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.provider.token.DefaultTokenServices

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
    fun authController(
            defaultTokenServices: DefaultTokenServices,
            authService: AuthService
    ): AuthController
            = AuthControllerImpl(authService, defaultTokenServices)
}
