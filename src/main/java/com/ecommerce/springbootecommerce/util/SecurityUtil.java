package com.ecommerce.springbootecommerce.util;

import com.ecommerce.springbootecommerce.dto.MyAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class SecurityUtil {
    private SecurityUtil() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static MyAccount getPrincipal() {
        MyAccount myAccount = (MyAccount) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
        return myAccount;
    }
    
    public static List<String> getAuthorities() {
        List<String> result = new ArrayList<>();

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            result.add(authority.getAuthority());
        }

        return result;
    }
}

