package com.ecommerce.springbootecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO extends BaseDTO<AccountDTO> {
    
    private String address;   
    private Double balance;
    private String phoneNumber;
    
    private String username;
    
    private String fullName;
    
    private String email;
    
    private String password;    
    
    private String rePassword;
    
    private String newPassword;
    private String ReNewPassword;
    
    private String typeEditProfile;
    
    private boolean status;
}
