package com.lucasmartins.api.config.security.provider;


import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthenticationProvider extends DaoAuthenticationProvider {

    public AuthenticationProvider(UserDetailsService detailsService) {
        super.setUserDetailsService(detailsService);
    }

    public AuthenticationProvider passwordEncoder(PasswordEncoder passwordEncoder) {
        super.setPasswordEncoder(passwordEncoder);
        return this;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UserAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
