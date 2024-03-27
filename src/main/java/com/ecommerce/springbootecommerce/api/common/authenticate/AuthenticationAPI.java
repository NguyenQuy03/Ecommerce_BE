package com.ecommerce.springbootecommerce.api.common.authenticate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.api.common.authenticate.payload.request.LogInRequest;
import com.ecommerce.springbootecommerce.api.common.authenticate.payload.request.RegisterRequest;
import com.ecommerce.springbootecommerce.api.common.authenticate.payload.response.AuthResponse;
import com.ecommerce.springbootecommerce.constant.AlertConstant;
import com.ecommerce.springbootecommerce.constant.JWTConstant;
import com.ecommerce.springbootecommerce.constant.RedisConstant;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.service.impl.AccountService;
import com.ecommerce.springbootecommerce.service.impl.CustomUserDetailsService;
import com.ecommerce.springbootecommerce.util.CookieUtil;
import com.ecommerce.springbootecommerce.util.JwtUtil;
import com.ecommerce.springbootecommerce.util.RedisUtil;
import com.ecommerce.springbootecommerce.util.SecurityUtil;

@RestController(value = "AuthAPIOfUser")
@RequestMapping("/api/v1/auth")
public class AuthenticationAPI {

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request) {
        try {
            Map<String, String> errorFeedBack = new HashMap<>();

            if (request.getPassword() != null && request.getConfirmPassword() != null
                    && !request.getPassword().equals(request.getConfirmPassword())) {
                errorFeedBack.put("confirmPassword", AlertConstant.ALERT_WRONG_CONFIRM_PASSWORD);
            }

            if (!request.getUsername().matches("^[a-zA-Z0-9]*$")) {
                errorFeedBack.put("username", AlertConstant.ALERT_WRONG_REGEX);
            }

            if (request.getUsername() != null && accountService.isAccountExistByUsername(request.getUsername())) {
                errorFeedBack.put("username", AlertConstant.ALERT_USERNAME_EXISTED);
            }

            if (request.getEmail() != null && accountService.isAccountExistByEmail(request.getEmail())) {
                errorFeedBack.put("email", AlertConstant.ALERT_EMAIL_EXISTED);
            }

            if (!errorFeedBack.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(AuthResponse.builder().errorFeedBack(errorFeedBack).build());
            }

            accountService.register(request);
            return ResponseEntity.ok().body(AlertConstant.ALERT_SUCCESS_REGISTATION);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponse());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LogInRequest request,
            HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {

        if (request.getUsername().isEmpty() || request.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AlertConstant.ALERT_MISSING_UNAME_OR_PASS);
        }

        try {
            AuthResponse authResponse = accountService.authenticate(request, httpRequest);
            if (authResponse != null) {
                List<String> roles = SecurityUtil.getAuthorities();
                String fullName = SecurityUtil.getPrincipal().getFullName();

                httpResponse.addCookie(cookieUtil.initCookie("refresh_token", authResponse.getRefreshToken(),
                        (int) JWTConstant.JWT_REFRESH_TOKEN_EXPIRATION));

                return ResponseEntity.ok().body(
                        AuthResponse.builder()
                                .accessToken(authResponse.getAccessToken())
                                .roles(roles)
                                .fullName(fullName)
                                .build());
            } else {
                // Handle authentication failure
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AlertConstant.ALERT_MESSAGE_LOGIN_FAILURE);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponse());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            HttpServletRequest httpRequest, HttpServletResponse httpResponse, Authentication authentication)
            throws IOException {
        try {
            String refreshToken = cookieUtil.getCookie(httpRequest.getCookies(), "refresh_token");
            if (refreshToken != null) {
                String username = jwtUtil.extractUsername(refreshToken);

                redisUtil.removeKey(RedisConstant.REDIS_JWT_BRANCH + username);
                cookieUtil.removeAll(httpRequest.getCookies(), httpResponse);
            }

            new SecurityContextLogoutHandler().logout(httpRequest, httpResponse, authentication);

            return ResponseEntity.ok().body("Logged out successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponse());
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request) {

        try {
            String refreshToken = cookieUtil.getCookie(request.getCookies(), "refresh_token");
            // Handle empty refresh token
            if (refreshToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(AlertConstant.ALERT_MESSAGE_TOKEN_EXPIRED);
            }

            String username = jwtUtil.extractUsername(refreshToken);

            // Validate with prev refresh token
            String prevRefeshToken = redisUtil.getKey(RedisConstant.REDIS_JWT_BRANCH + username);
            if (!prevRefeshToken.equals(refreshToken)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Refresh token is not valid");
            }

            CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            String accessToken = jwtUtil.generateAccessToken(userDetails);

            return ResponseEntity.ok().body(
                    AuthResponse.builder()
                            .accessToken(accessToken)
                            .fullName(userDetails.getFullName())
                            .roles(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponse());
        }
    }
}
