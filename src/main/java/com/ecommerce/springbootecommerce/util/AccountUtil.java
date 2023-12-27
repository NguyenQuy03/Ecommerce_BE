package com.ecommerce.springbootecommerce.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.service.IAccountService;

@Component
public class AccountUtil {

    @Autowired
    private IAccountService accountService;

    public AccountDTO getCurAccount() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return accountService.findById(userDetails.getId());
    }

    public long getCurAccountId() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getId();
    }
}
