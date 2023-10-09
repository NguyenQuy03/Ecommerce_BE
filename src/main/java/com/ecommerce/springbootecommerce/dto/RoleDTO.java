package com.ecommerce.springbootecommerce.dto;

import java.util.List;

import lombok.Data;

@Data
public class RoleDTO {

    private String code;
    private List<AccountDTO> accounts;

}
