package by.mksn.kwitapi.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    //private static final Logger LOG = LoggerFactory.getLogger("application");

    private final JdbcUserDetailsService userDetailsService;
    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfiguration(JdbcUserDetailsService userDetailsService,
                                 DataSource dataSource,
                                 PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
//        return new JdbcTokenStore(dataSource) {
//
//            @Override
//            public OAuth2AccessToken readAccessToken(String tokenValue) {
//                OAuth2AccessToken accessToken = null;
//                try {
//                    accessToken = new DefaultOAuth2AccessToken(tokenValue);
//                }
//                catch (EmptyResultDataAccessException e) {
//                    if (LOG.isInfoEnabled()) {
//                        LOG.info("Failed to find access token for token "+tokenValue);
//                    }
//                }
//                catch (IllegalArgumentException e) {
//                    LOG.warn("Failed to deserialize access token for " +tokenValue,e);
//                    removeAccessToken(tokenValue);
//                }
//                return accessToken;
//            }
//
//        };
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.anonymous().disable();
//        http.authorizeRequests()
//                .antMatchers("/oauth/token").permitAll()
//                .anyRequest().authenticated();
//    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
