package com.ecommerce.springbootecommerce.api.authenticate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.api.authenticate.payload.request.LogInRequest;
import com.ecommerce.springbootecommerce.api.authenticate.payload.request.RegisterRequest;
import com.ecommerce.springbootecommerce.api.authenticate.payload.response.AuthResponse;
import com.ecommerce.springbootecommerce.constant.AlertConstant;
import com.ecommerce.springbootecommerce.constant.JWTConstant;
import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.service.impl.AccountService;
import com.ecommerce.springbootecommerce.util.CookieUtil;
import com.ecommerce.springbootecommerce.util.SecurityUtil;

@RestController
@RequestMapping("/api/")
public class AuthenticationAPI {
    @Autowired
    private AccountService accountService;

    @Autowired
    private CookieUtil cookieUtil;

    @PostMapping("v1/auth/register")
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

    @PostMapping("v2/auth/login")
    public ResponseEntity<?> loginV2(
            @RequestBody LogInRequest request,
            HttpServletRequest httpServletRequest) throws IOException {
        try {
            String accessToken = accountService.authenticate(request, httpServletRequest);
            if (accessToken != null) {
                List<String> roles = SecurityUtil.getAuthorities();

                return ResponseEntity.ok().body(
                        AuthResponse.builder()
                                .accessToken(accessToken)
                                .roles(roles)
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

    @PostMapping("v1/auth/login")
    public void loginV1(
            LogInRequest request,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        String jwt = accountService.authenticate(request, httpServletRequest);
        if (jwt == null) {
            Cookie cookie = cookieUtil.initCookie(SystemConstant.LOGIN_FAILURE_DTO,
                    AlertConstant.ALERT_MESSAGE_LOGIN_FAILURE, AlertConstant.ALERT_MESSAGE_LOGIN_EXPIRATION);
            httpServletResponse.addCookie(cookie);
            httpServletResponse.sendRedirect("/login");
        } else {
            List<String> roles = SecurityUtil.getAuthorities();
            String route = handleRoute(roles);
            Cookie cookie = cookieUtil.initCookie(SystemConstant.COOKIE_JWT_HEADER, SystemConstant.TOKEN_JWT_TYPE + jwt,
                    JWTConstant.JWT_COOKIE_ACCESS_TOKEN_EXPIRATION);

            httpServletResponse.addCookie(cookie);
            httpServletResponse.sendRedirect(route);
        }

    }

    private String handleRoute(List<String> roles) {
        if (roles.contains("ROLE_MANAGER")) {
            return "/manager/transaction";
        } else if (roles.contains("ROLE_SELLER")) {
            return "/seller/recentSales";
        } else if (roles.contains("ROLE_BUYER")) {
            return "/home";
        }
        return "redirect:/error";
    }
}
