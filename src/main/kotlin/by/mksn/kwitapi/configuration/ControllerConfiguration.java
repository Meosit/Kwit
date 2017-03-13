package by.mksn.kwitapi.configuration;

import by.mksn.kwitapi.controller.TestController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfiguration {

    @Bean
    public TestController testController() {
        return new TestController();
    }

}
