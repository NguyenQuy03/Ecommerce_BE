package com.ecommerce.springbootecommerce.constant.enums.account;

import java.util.Set;

import com.ecommerce.springbootecommerce.constant.SystemConstant;

public enum AccountRole {
    ALL(Set.of(SystemConstant.ROLE_BUYER, SystemConstant.ROLE_SELLER)),
    BUYER(Set.of(SystemConstant.ROLE_BUYER)),
    SELLER(Set.of(SystemConstant.ROLE_SELLER));

    private Set<String> roles;

    AccountRole(Set<String> roles) {
        this.roles = roles;
    }

    public static Set<String> getRoles(String type) {
        for (AccountRole accountRole : AccountRole.values()) {
            if (accountRole.name().equalsIgnoreCase(type)) {
                return accountRole.roles;
            }
        }

        return null;
    }
}