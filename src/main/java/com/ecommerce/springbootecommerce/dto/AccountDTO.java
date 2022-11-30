package com.ecommerce.springbootecommerce.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    
    private String address;   
    private Double balance;
    private String phoneNumber;
    
    @NotBlank(message = "Please enter your user name!")
    private String userName;
    
    @NotBlank(message = "Please enter your full name!")
    private String fullName;
    
    @NotBlank(message = "Please enter your email!")
    @Email(message = "Please enter a valid email address")
    private String email;
    
    @NotBlank(message = "Please enter your password!")
    @Length(min = 3, message = "Password must be at least 3 characters")
    private String password;
    
    @NotBlank(message = "Please re-enter your password!")
    private String rePassword;
}
