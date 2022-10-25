package com.ecommerce.springbootecommerce.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    
    @SuppressWarnings("unchecked")
    public static List<String> getAuthorities() {
        List<String> result = new ArrayList<>();

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            result.add(authority.getAuthority());
        }

        return result;
    }
}

