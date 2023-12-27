package com.ecommerce.springbootecommerce.api.authenticate.payload.response;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    Map<String, String> errorFeedBack;

    List<String> roles;

    @JsonProperty("access_token")
    private String accessToken;
}
