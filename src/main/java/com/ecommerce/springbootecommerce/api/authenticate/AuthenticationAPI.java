package com.ecommerce.springbootecommerce.api.authenticate;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.api.authenticate.payload.request.LogInRequest;
import com.ecommerce.springbootecommerce.api.authenticate.payload.request.RegisterRequest;
import com.ecommerce.springbootecommerce.constant.AlertConstant;
import com.ecommerce.springbootecommerce.constant.JWTConstant;
import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.service.impl.AccountService;
import com.ecommerce.springbootecommerce.util.CookieUtil;
import com.ecommerce.springbootecommerce.util.SecurityUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationAPI {
    @Autowired
    private AccountService accountService;

    @Autowired
    private CookieUtil cookieUtil;

    @PostMapping("/register")
    public void register(
            @RequestBody RegisterRequest request
    ){
        try {
            accountService.register(request);
        } catch (Exception e) {
            throw new RuntimeException("Error register");
        }
    }
    
    @PostMapping("/login")
    public void login(
            LogInRequest request,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) throws IOException {
        String jwt = accountService.authenticate(request, httpServletRequest);
        if(jwt == null){
            Cookie cookie = cookieUtil.initCookie(SystemConstant.LOGIN_FAILURE_DTO, AlertConstant.ALERT_MESSAGE_LOGIN_FAILURE, AlertConstant.ALERT_MESSAGE_LOGIN_EXPIRATION);
            httpServletResponse.addCookie(cookie);
            httpServletResponse.sendRedirect("/login");
        }
        else {
            List<String> roles = SecurityUtil.getAuthorities();
            String route = handleRoute(roles);
            Cookie cookie = cookieUtil.initCookie(SystemConstant.COOKIE_JWT_HEADER,SystemConstant.TOKEN_JWT_TYPE + jwt, JWTConstant.JWT_COOKIE_ACCESS_TOKEN_EXPIRATION);

            httpServletResponse.addCookie(cookie);
            httpServletResponse.sendRedirect(route);
        }

    }

    private String handleRoute(List<String> roles) {
        if(roles.contains("ROLE_MANAGER")){
            return "/manager/transaction";
        } else if(roles.contains("ROLE_SELLER")) {
            return "/seller/recentSales";
        } else if(roles.contains("ROLE_BUYER")) {
            return "/home";
        }
        return "redirect:/error";
    }

}
