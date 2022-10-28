package com.ecommerce.springbootecommerce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.ecommerce.springbootecommerce.security.CustomLoginHandler;
import com.ecommerce.springbootecommerce.security.CustomLogoutHandle;
import com.ecommerce.springbootecommerce.service.impl.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private EncoderConfig encoderConfig;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomLoginHandler customLoginHandler;

    @Autowired
    private CustomLogoutHandle customLogoutHandle;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/register**", "/admin/vendor/**")
                .permitAll()
                .antMatchers("/home").access("hasRole('ROLE_BUYER')")
                .antMatchers("/seller/home").access("hasRole('ROLE_SELLER')")
                .antMatchers("/admin/home").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .anyRequest().authenticated()
                .and()
                //LOGIN
                .formLogin().loginPage("/login").permitAll()
                .loginProcessingUrl("/j_spring_security_check")
                .successHandler(customLoginHandler)
                .failureUrl("/login?result=failure")
                .and()
                //LOGOUT
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(customLogoutHandle)
                ;
        ;

        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(encoderConfig.passwordEncoder());
    }
}
