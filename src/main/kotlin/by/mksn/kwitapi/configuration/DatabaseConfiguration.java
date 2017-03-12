package by.mksn.kwitapi.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories("by.mksn.kwitapi.repository")
@EntityScan(basePackages = "by.mksn.kwitapi.entity")
public class DatabaseConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

//    @Bean(name="entityManager")
//    public LocalContainerEntityManagerFactoryBean mySqlEntityManagerFactory(
//            EntityManagerFactoryBuilder builder){
//        return builder.dataSource(dataSource())
//                .packages("com.test.entity.db.mysql")
//                .build();
//    }

}
