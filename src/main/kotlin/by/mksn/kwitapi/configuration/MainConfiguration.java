package by.mksn.kwitapi.configuration;

import by.mksn.kwitapi.configuration.security.AuthorizationServerConfiguration;
import by.mksn.kwitapi.configuration.security.MethodSecurityConfiguration;
import by.mksn.kwitapi.configuration.security.ResourceServerConfiguration;
import by.mksn.kwitapi.configuration.security.SecurityConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Import({ControllerConfiguration.class,
        DatabaseConfiguration.class,
        ServiceConfiguration.class,
        SecurityConfiguration.class,
        ResourceServerConfiguration.class,
        AuthorizationServerConfiguration.class,
        MethodSecurityConfiguration.class
})
@EnableAutoConfiguration
public class MainConfiguration extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MainConfiguration.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
