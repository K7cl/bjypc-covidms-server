package com.k7cl.bjypc.covid.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .authorizeHttpRequests((authz) -> authz
                        .antMatchers(HttpMethod.POST, "/login").permitAll()
                        .antMatchers(HttpMethod.OPTIONS, "/login").permitAll()
//                        .antMatchers(HttpMethod.GET, "/swagger-ui/*").permitAll()
//                        .antMatchers(HttpMethod.GET, "/v3/*").permitAll()
//                        .antMatchers(HttpMethod.GET, "/v3/api-docs/*").permitAll()
                        .anyRequest().authenticated()
//                            .anyRequest().permitAll()
                );
        return http.build();
    }
}