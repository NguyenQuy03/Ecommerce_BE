package com.ecommerce.springbootecommerce.api.authenticate;

import com.ecommerce.springbootecommerce.api.authenticate.payload.request.LogInRequest;
import com.ecommerce.springbootecommerce.api.authenticate.payload.request.RegisterRequest;
import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.service.impl.AuthenticationService;
import com.ecommerce.springbootecommerce.util.CookieUtil;
import com.ecommerce.springbootecommerce.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationAPI {
    
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CookieUtil cookieUtil;

    @PostMapping("/register")
    public void register(
            @RequestBody RegisterRequest request
    ){
        authenticationService.register(request);
    }
    
    @PostMapping("/login")
    public void login(
            LogInRequest request,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) throws IOException {
        String jwt = authenticationService.authenticate(request, httpServletRequest);
        if(jwt == null){
            Cookie cookie = cookieUtil.setCookie("loginFailure", "Your username or password is incorrect", 5);
            httpServletResponse.addCookie(cookie);
            httpServletResponse.sendRedirect("/login");
        }
        else {
            List<String> roles = SecurityUtil.getAuthorities();
            String route = handleRoute(roles);
            Cookie cookie = cookieUtil.setCookie("Jwt","Bearer " + jwt, SystemConstant.JWT_COOKIE_ACCESS_TOKEN_EXPIRATION);

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
