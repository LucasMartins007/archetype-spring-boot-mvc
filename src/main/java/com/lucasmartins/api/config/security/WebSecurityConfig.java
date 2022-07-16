package com.lucasmartins.api.config.security;

import org.springframework.context.annotation.Configuration;

/**
 * As {@link org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter} is Deprecated
 * in 5.7.0-M2 version, the security configuration should be implemented using a component-based security configuration,
 * as described in {@see https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter }
 */
@Configuration
public class WebSecurityConfig {
}
