package by.mksn.kwit.configuration

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
                "by.mksn.kwit.service",
                "by.mksn.kwit.repository",
                "by.mksn.kwit.entity",
                "by.mksn.kwit.controller"
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