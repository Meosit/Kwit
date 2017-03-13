package by.mksn.kwitapi.configuration;

import by.mksn.kwitapi.configuration.security.JdbcUserDetailsService;
import by.mksn.kwitapi.repository.UserRepository;
import by.mksn.kwitapi.service.UserService;
import by.mksn.kwitapi.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;

public class ServiceConfiguration {

    @Bean
    public JdbcUserDetailsService userDetailsService(UserService userService) {
        return new JdbcUserDetailsService(userService);
    }

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }


}
