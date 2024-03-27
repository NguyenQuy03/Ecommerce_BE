package com.ecommerce.springbootecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO extends BaseDTO<AccountDTO> {

    private String address;
    private String phoneNumber;

    private String username;
    private String fullName;
    private String email;

    private String mainRole;

    private String password;
    private String confirmPassword;

    private String newPassword;
    private String reNewPassword;

    private String typeEditProfile;

    private boolean isActive;
}
