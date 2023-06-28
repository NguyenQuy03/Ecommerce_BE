package com.ecommerce.springbootecommerce.api.authenticate.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Please enter your user name!")
    private String username;
    
    @NotBlank(message = "Please enter your full name!")
    private String fullName;
    
    @NotBlank(message = "Please enter your email!")
    @Email(message = "Please enter a valid email address")
    private String email;
    
    @NotBlank(message = "Please enter your password!")
    @Length(min = 3, message = "Password must be at least 3 characters")
    private String password;
    private String rePassword;
}
