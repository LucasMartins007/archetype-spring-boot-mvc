package com.lucasmartins.api.config.security;

import com.lucasmartins.api.config.security.provider.AuthenticationProvider;
import com.lucasmartins.common.utils.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

/**
 * As {@link org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter} is Deprecated
 * in 5.7.0-M2 version, the security configuration should be implemented using a component-based security configuration,
 * as described in {@see https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter }
 */
@Configuration
public class WebSecurityConfig {

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Value("${api.base-path}/**")
    private String prefixPath;

    private static final List<String> NO_SECURED_URLS = List.of("/v3/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/configuration/security",
            "/webjars/**",
            "/actuator/**",
            "/example");

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> auth
                        .antMatchers(ListUtil.toArray(NO_SECURED_URLS))
                        .permitAll()
                        .antMatchers(prefixPath)
                        .authenticated()
                        .anyRequest()
                        .denyAll()
                )
                .formLogin()
                .disable()
                .csrf()
                .disable()
                .httpBasic()
                .disable()
                .build();
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth, UserDetailsService detailsService) {
        auth.authenticationProvider(new AuthenticationProvider(detailsService).passwordEncoder(passwordEncoder));
    }

}
