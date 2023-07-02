package com.ecommerce.springbootecommerce.security;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.entity.AccountEntity;
import com.ecommerce.springbootecommerce.repository.AccountRepository;
import com.ecommerce.springbootecommerce.service.impl.CustomUserDetailsService;
import com.ecommerce.springbootecommerce.util.CookieUtil;
import com.ecommerce.springbootecommerce.util.JwtUtil;
import com.ecommerce.springbootecommerce.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class JwtAuthenticationFilter extends OncePerRequestFilter{

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
        if (path.contains("/api/auth") || path.contains("/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();
        String token = null;
        String jwt = "", username = "";
        if(cookies == null || SecurityContextHolder.getContext().getAuthentication() == null) {
            filterChain.doFilter(request, response);
            return;
        } else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Jwt")) {
                    token = cookie.getValue();
                    if ( token == null || !token.startsWith("Bearer") ) {
                        filterChain.doFilter(request, response);
                        return;
                    }

                    jwt = token.substring(7);
                    if (SecurityContextHolder.getContext().getAuthentication() != null) {
                        username = SecurityContextHolder.getContext().getAuthentication().getName();
                    }

                    var account = AccountEntity.builder().username(username).build();
                    if(jwtUtil.isTokenValid(jwt, account)) {
                        filterChain.doFilter(request, response);
                        return;
                    }
                    break;
                }
            }
        }

        /* Handle if token is expired */
        String refreshToken = (String) redisUtil.getKey("Jwt:" + username);
        if(refreshToken == null) {
            response.sendRedirect("/login");
        } else {
            var account = AccountEntity.builder()
                    .username(username).build();
            jwt = jwtUtil.generateAccessToken(account);
            Cookie cookie = cookieUtil.setCookie("Jwt", "Bearer " + jwt, SystemConstant.JWT_COOKIE_ACCESS_TOKEN_EXPIRATION);
            response.addCookie(cookie);
        }

        filterChain.doFilter(request, response);
    }
}