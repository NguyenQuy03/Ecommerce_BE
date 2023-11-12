package com.ecommerce.springbootecommerce.dto;

import com.ecommerce.springbootecommerce.entity.AccountEntity;
import com.ecommerce.springbootecommerce.entity.RoleEntity;

import lombok.Data;

@Data
public class AccountRoleDTO extends BaseDTO<AccountRoleDTO>{
    private Long accountId;
    private String roleCode;
    
    private AccountEntity account;
    private RoleEntity role;
}