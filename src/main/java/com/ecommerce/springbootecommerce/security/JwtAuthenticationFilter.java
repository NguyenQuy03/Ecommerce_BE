package com.ecommerce.springbootecommerce.security;

import com.ecommerce.springbootecommerce.entity.AccountEntity;
import com.ecommerce.springbootecommerce.repository.AccountRepository;
import com.ecommerce.springbootecommerce.service.impl.CustomUserDetailsService;
import com.ecommerce.springbootecommerce.util.CookieUtil;
import com.ecommerce.springbootecommerce.util.JwtUtil;
import com.ecommerce.springbootecommerce.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisUtil redisUtil;
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CookieUtil cookieUtil;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        String path = request.getServletPath();
        if (path.contains("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();
        String authHeader = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("Jwt")) {
                authHeader = cookie.getValue();
                break;
            }
        }

        String jwt, userName;
        if (authHeader != null && !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        if(authHeader == null && SecurityContextHolder.getContext().getAuthentication() != null) {
            userName = SecurityContextHolder.getContext().getAuthentication().getName();
            String refreshToken = (String) redisUtil.getKey("Jwt:" + userName);
            if(refreshToken.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            } else {
                AccountEntity account = accountRepository.findByUsername(userName).get();
                jwt = jwtUtil.generateAccessToken(account);
                Cookie cookie = cookieUtil.setCookie("Jwt", "Bearer " + jwt, null);
                response.addCookie(cookie);
            }
        }


//        if (userName != null && auth == null) {
//            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
//            var accountEntity = AccountEntity.builder()
//                                .username(userDetails.getUsername())
//                                .password(userDetails.getPassword())
//                                .build();
//            if (jwtUtil.isTokenValid(jwt, accountEntity)) {
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        userDetails, null,
//                        userDetails.getAuthorities()
//                );
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
        
        filterChain.doFilter(request, response);
    }
}