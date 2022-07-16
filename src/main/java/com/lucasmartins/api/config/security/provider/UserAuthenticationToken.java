package com.lucasmartins.api.config.security.provider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class UserAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public UserAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

}