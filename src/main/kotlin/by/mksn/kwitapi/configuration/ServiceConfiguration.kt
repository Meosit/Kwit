package by.mksn.kwitapi.configuration

import by.mksn.kwitapi.repository.*
import by.mksn.kwitapi.service.*
import by.mksn.kwitapi.service.impl.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceConfiguration {

    @Bean
    fun currencyService(currencyRepository: CurrencyRepository): CurrencyService
            = CurrencyServiceImpl(currencyRepository)

    @Bean
    fun walletService(
            walletRepository: WalletRepository,
            transactionRepository: TransactionRepository
    ): WalletService = WalletServiceImpl(walletRepository, transactionRepository)

    @Bean
    fun categoryService(
            categoryRepository: CategoryRepository,
            transactionRepository: TransactionRepository
    ): CategoryService = CategoryServiceImpl(categoryRepository, transactionRepository)

    @Bean
    fun transactionService(transactionRepository: TransactionRepository): TransactionService
            = TransactionServiceImpl(transactionRepository)

    @Bean
    fun remittanceService(
            remittanceRepository: RemittanceRepository,
            walletRepository: WalletRepository
    ): RemittanceService
            = RemittanceServiceImpl(remittanceRepository, walletRepository)

}