package com.ecommerce.springbootecommerce.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.util.SecurityUtil;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class CustomLoginHandler extends SimpleUrlAuthenticationSuccessHandler{

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    private String determineTargetUrl(Authentication authentication) {
        String url = "";
        List<String> roles = SecurityUtil.getAuthorities();

        if (isAdmin(roles)) {
            url = "/admin/home";
        } else if (isSeller(roles)) {
            url = "/seller/home";
        } else if (isBuyer(roles)) {
            url = "/home";
        }

        return url;
    }

    private boolean isAdmin(List<String> roles) {
        boolean result = false;
        if (roles.contains("ROLE_ADMIN") || roles.contains("ROLE_MANAGER")) {
            return true;
        }
        return result;
    }
    
    private boolean isBuyer(List<String> roles) {
        return roles.contains("ROLE_BUYER");
    }

    private boolean isSeller(List<String> roles) {
        return roles.contains("ROLE_SELLER");
    }
}