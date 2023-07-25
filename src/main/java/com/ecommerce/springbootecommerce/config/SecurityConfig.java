
package com.ecommerce.springbootecommerce.config;

import com.ecommerce.springbootecommerce.security.CustomLogoutHandle;
import com.ecommerce.springbootecommerce.security.JwtAuthenticationFilter;
import com.ecommerce.springbootecommerce.service.impl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private AuthenticationConfig authenticationConfig;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomLogoutHandle customLogoutHandle;

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.GET).permitAll()
                .antMatchers(
                        "/api/auth/**", "/register**",
                        "/home/**", "/admin/vendor/**", "/admin/js/main.js",
                        "/common/**", "/buyer/**",
                        "/favicon.ico"
                )
                .permitAll()
                .antMatchers("/seller/**").hasRole("SELLER")
                .antMatchers("/manager/**").hasRole("MANAGER")
                .anyRequest().authenticated()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                //LOGIN
                .formLogin().loginPage("/login").permitAll()
            .and()
                //LOGOUT
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(customLogoutHandle)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());

        http
                .securityContext()
                .securityContextRepository(httpSessionSecurityContextRepository());
        
        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(authenticationConfig.passwordEncoder());
    }

    @Bean
    public HttpSessionSecurityContextRepository httpSessionSecurityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

}
