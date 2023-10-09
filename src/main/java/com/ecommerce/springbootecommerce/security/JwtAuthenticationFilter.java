package com.ecommerce.springbootecommerce.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecommerce.springbootecommerce.constant.AlertConstant;
import com.ecommerce.springbootecommerce.constant.JWTConstant;
import com.ecommerce.springbootecommerce.constant.RedisConstant;
import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.util.CookieUtil;
import com.ecommerce.springbootecommerce.util.JwtUtil;
import com.ecommerce.springbootecommerce.util.RedisUtil;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CookieUtil cookieUtil;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        String path = request.getServletPath();
        if (path.contains("/api/auth") || path.contains("/login") || path.contains("/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        if(!request.getMethod().equals("GET")) {

            Cookie[] cookies = request.getCookies();
            String token = null;
            if(cookies == null || SecurityContextHolder.getContext().getAuthentication() == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("/login");
                return;
            } else {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(SystemConstant.COOKIE_JWT_HEADER)) {
                        token = cookie.getValue();
                        if (token == null || !token.startsWith("Bearer") ) {
                            filterChain.doFilter(request, response);
                            return;
                        }
    
                        String accessToken = token.substring(7);
                        UserDetails account = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                        
                        /* Handle token expired */
                        if(!jwtUtil.isTokenExpired(accessToken)) {
                            String refreshToken = redisUtil.getKey(RedisConstant.REDIS_JWT_BRANCH + account.getUsername());
                            if(refreshToken == null) {
                                Cookie newCookie = cookieUtil.initCookie("loginFailure", AlertConstant.ALERT_MESSAGE_TOKEN_EXPIRED, AlertConstant.ALERT_MESSAGE_LOGIN_EXPIRATION);
                                response.addCookie(newCookie);
                                response.sendRedirect("/login");
                            } else {
                                String newJwt = jwtUtil.generateAccessToken(account);
                                Cookie newCookie = cookieUtil.initCookie(SystemConstant.COOKIE_JWT_HEADER, SystemConstant.TOKEN_JWT_TYPE + newJwt, JWTConstant.JWT_COOKIE_ACCESS_TOKEN_EXPIRATION);
                                response.addCookie(newCookie);
                                filterChain.doFilter(request, response);
                            }
                        } else {
                            filterChain.doFilter(request, response);
                        }
    
                        return;
                    }
                }

                // Handle if cookie does not have JWT
                if(token == null) {
                    Cookie newCookie = cookieUtil.initCookie("loginFailure", AlertConstant.ALERT_MESSAGE_NOT_PERMISSION, AlertConstant.ALERT_MESSAGE_LOGIN_EXPIRATION);
                    response.addCookie(newCookie);
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    response.sendRedirect("/login");
                    return;
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}