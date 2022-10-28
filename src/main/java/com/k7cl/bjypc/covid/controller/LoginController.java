package com.k7cl.bjypc.covid.controller;

import com.k7cl.bjypc.covid.config.UserAuthenticationManager;
import com.k7cl.bjypc.covid.pojo.LoginBody;
import com.k7cl.bjypc.covid.pojo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class LoginController {
    @Autowired
    private UserAuthenticationManager userAuthenticationManager;

    @PostMapping("/login")
    public Response login(@RequestBody LoginBody loginBody) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginBody.userName(), loginBody.password());
            Authentication authentication = userAuthenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new Response(true, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, e.getMessage(), null);
        }
    }

}
