package by.mksn.kwitapi.configuration

import by.mksn.kwitapi.repository.CategoryRepository
import by.mksn.kwitapi.service.CategoryService
import by.mksn.kwitapi.service.impl.CategoryServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ServiceConfiguration {

    @Bean
    fun categoryService(categoryRepository: CategoryRepository): CategoryService
            = CategoryServiceImpl(categoryRepository)

}