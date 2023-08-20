package com.ecommerce.springbootecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO extends BaseDTO<AccountDTO> {
    
    private String address;

    private String phoneNumber;
    
    private String username;
    private String fullName;
    private String email;
    private Set<String> roleCodes;
    
    private String password;
    private String rePassword;
    
    private String newPassword;
    private String ReNewPassword;
    
    private String typeEditProfile;
    
    private boolean status;
}
