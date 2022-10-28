package com.k7cl.bjypc.covid.config;

import com.k7cl.bjypc.covid.pojo.AuthenticationToken;
import com.k7cl.bjypc.covid.pojo.SessionUser;
import com.k7cl.bjypc.covid.service.impl.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class UserAuthenticationManager implements AuthenticationManager {

    @Autowired
    AdminService adminService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        if (password == null || Objects.equals(password,""))
            throw new BadCredentialsException("Password Empty!");

        if (!adminService.authenticate(userName, password)) {
            throw new BadCredentialsException("Username or Password Incorrect!");
        }

        SessionUser sessionUser = new SessionUser(userName);
        AuthenticationToken authenticationToken = new AuthenticationToken(sessionUser);
        authenticationToken.setAuthenticated(true);
        return authenticationToken;
    }

}
