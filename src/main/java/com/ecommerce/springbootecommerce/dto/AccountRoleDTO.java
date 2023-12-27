package com.ecommerce.springbootecommerce.dto;

import com.ecommerce.springbootecommerce.entity.AccountEntity;
import com.ecommerce.springbootecommerce.entity.RoleEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRoleDTO extends BaseDTO<AccountRoleDTO>{
    private Long accountId;
    private String roleCode;
    
    private AccountEntity account;
    private RoleEntity role;
}