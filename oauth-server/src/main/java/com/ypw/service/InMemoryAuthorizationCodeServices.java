package com.ypw.service;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {
    protected final ConcurrentHashMap<String, OAuth2Authentication> authorizationCodeStore = new ConcurrentHashMap();

    public InMemoryAuthorizationCodeServices() {
    }

    protected void store(String code, OAuth2Authentication authentication) {
        this.authorizationCodeStore.put(code, authentication);
    }

    public OAuth2Authentication remove(String code) {
        OAuth2Authentication auth = (OAuth2Authentication) this.authorizationCodeStore.remove(code);
        return auth;
    }
}