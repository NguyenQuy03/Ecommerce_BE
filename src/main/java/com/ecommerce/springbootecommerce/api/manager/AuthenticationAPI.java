package com.ecommerce.springbootecommerce.api.manager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.api.common.authenticate.payload.request.RegisterRequest;
import com.ecommerce.springbootecommerce.api.common.authenticate.payload.response.AuthResponse;
import com.ecommerce.springbootecommerce.constant.AlertConstant;
import com.ecommerce.springbootecommerce.exception.CustomException;
import com.ecommerce.springbootecommerce.service.IAccountService;

@RestController(value = "authAPIOfManager")
@RequestMapping("/api/v1/auth/manager")
public class AuthenticationAPI {

    private final IAccountService accountService;

    public AuthenticationAPI(IAccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(
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
            return ResponseEntity.ok().body("Add Account Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(((CustomException) e.fillInStackTrace()).getErrorCode()).body(e.getMessage());

        }
    }
}
