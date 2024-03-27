package com.ecommerce.springbootecommerce.api.common.authenticate.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogInRequest {
    private String username;
    private String password;
}
