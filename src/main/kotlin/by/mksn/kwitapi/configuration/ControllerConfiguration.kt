package by.mksn.kwitapi.configuration

import by.mksn.kwitapi.controller.*
import by.mksn.kwitapi.controller.exception.RestExceptionHandler
import by.mksn.kwitapi.controller.impl.*
import by.mksn.kwitapi.service.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ControllerConfiguration {

    @Bean
    fun responseEntityExceptionHandler(): RestExceptionHandler
            = RestExceptionHandler()

//    @Bean
//    fun whiteLabelErrorController(): WhiteLabelErrorController
//        = WhiteLabelErrorController()

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

}
