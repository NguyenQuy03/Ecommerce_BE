package com.ecommerce.springbootecommerce.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleDTO {

    private String id;
    private String code;
    private List<AccountDTO> accounts;

}
