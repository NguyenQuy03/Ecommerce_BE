package com.ecommerce.springbootecommerce.service.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.api.authenticate.payload.request.LogInRequest;
import com.ecommerce.springbootecommerce.api.authenticate.payload.request.RegisterRequest;
import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.entity.AccountEntity;
import com.ecommerce.springbootecommerce.entity.AccountRoleEntity;
import com.ecommerce.springbootecommerce.entity.RoleEntity;
import com.ecommerce.springbootecommerce.repository.AccountRepository;
import com.ecommerce.springbootecommerce.repository.AccountRoleRepository;
import com.ecommerce.springbootecommerce.util.JwtUtil;
import com.ecommerce.springbootecommerce.util.RedisUtil;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private AccountRoleRepository accountRoleRepository;
    
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    
    public void register(RegisterRequest request) {

        // SAVE ACCOUNT TO DB
        var account = AccountEntity.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .status(SystemConstant.ACTIVE_STATUS)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        accountRepository.save(account);

        // ADD ACCOUNT_ROLE TO DB
        RoleEntity roleEntity = new RoleEntity(SystemConstant.ROLE_BUYER, "buyer");
        AccountRoleEntity accountRoleEntity = new AccountRoleEntity();
        accountRoleEntity.setAccount(account);
        accountRoleEntity.setRole(roleEntity);

        accountRoleRepository.save(accountRoleEntity);
      }

      public String authenticate(LogInRequest request, HttpServletRequest httpServletRequest) {
          String username = request.getUsername();
          try {
              authenticationManager.authenticate(
                      new UsernamePasswordAuthenticationToken(
                              username,
                              request.getPassword()
                      )
              );
          } catch (Exception e) {
              return null;
          }

          AccountEntity account = accountRepository.findByUsername(username).get();
          UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                  userDetails, null,
                  userDetails.getAuthorities()
          );
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
          SecurityContextHolder.getContext().setAuthentication(authToken);

          String accessToken = jwtUtil.generateAccessToken(account);
          String refreshToken = jwtUtil.generateRefreshToken(account);
          revokeAllUserTokens(account);
          redisUtil.setKey("Jwt:" + account.getUsername(), refreshToken, SystemConstant.JWT_REFRESH_TOKEN_EXPIRATION);
          return accessToken;
      }

      private void revokeAllUserTokens(AccountEntity account) {
        redisUtil.removeKey("Jwt:" + account.getUsername());

      }
//      public void refreshToken(
//              HttpServletRequest request,
//              HttpServletResponse response
//      ) {
//        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//          return;
//        }
//
//        String refreshToken = authHeader.substring(7);
//        String username = jwtUtil.extractUsername(refreshToken);
//        if (username != null) {
//          var account = accountRepository.findByUsername(username)
//                  .orElseThrow();
//          if (jwtUtil.isTokenValid(refreshToken, account)) {
//            var jwt = jwtUtil.generateAccessToken(account);
//            revokeAllUserTokens(account);
//
//            Cookie cookie = new Cookie("Jwt", jwt);
//            response.addCookie(cookie);
//          }
//        }
//      }
}
