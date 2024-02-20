package com.ecommerce.springbootecommerce.api.authenticate.payload.response;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private Map<String, String> errorFeedBack;

    private List<String> roles;

    private String fullName;

    private String accessToken;

    private String refreshToken;
}
