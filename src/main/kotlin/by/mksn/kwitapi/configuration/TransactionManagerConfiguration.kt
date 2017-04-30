package by.mksn.kwitapi.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource


@Configuration
@EnableTransactionManagement
class TransactionManagerConfiguration(
        val dataSource: DataSource
) {

    @Bean
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val bean = LocalContainerEntityManagerFactoryBean()
        bean.dataSource = dataSource
        bean.setPackagesToScan(
                "by.mksn.kwitapi.service",
                "by.mksn.kwitapi.repository",
                "by.mksn.kwitapi.entity",
                "by.mksn.kwitapi.controller"
        )
        bean.jpaVendorAdapter = HibernateJpaVendorAdapter()
        return bean
    }

    @Bean
    fun transactionManager(): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = entityManagerFactory().`object`
        return transactionManager
    }

}