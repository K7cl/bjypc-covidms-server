package com.k7cl.bjypc.covid.pojo;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class AuthenticationToken extends AbstractAuthenticationToken {

    private final SessionUser sessionUser;

    public AuthenticationToken(SessionUser sessionUser) {
        super(null);
        this.sessionUser = sessionUser;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    public SessionUser getSessionUser() {
        return sessionUser;
    }
}
