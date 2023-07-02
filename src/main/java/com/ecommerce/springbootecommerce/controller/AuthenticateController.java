package com.ecommerce.springbootecommerce.controller;

import com.ecommerce.springbootecommerce.api.authenticate.AuthenticationAPI;
import com.ecommerce.springbootecommerce.api.authenticate.payload.request.LogInRequest;
import com.ecommerce.springbootecommerce.api.authenticate.payload.request.RegisterRequest;
import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.converter.AccountConverter;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.AccountRoleDTO;
import com.ecommerce.springbootecommerce.entity.RoleEntity;
import com.ecommerce.springbootecommerce.service.IAccountRoleService;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class AuthenticateController {
    
    @Autowired
    private IAccountService accountService;
       
    @Autowired
    private IAccountRoleService accountRoleService;
    
    @Autowired
    private AccountConverter accountConverter;
    
    @Autowired
    private AuthenticationAPI authenticationAPI;

    @Autowired
    CookieUtil cookieUtil;
    
    @GetMapping("/login")
    public String login(
            Model model, HttpServletRequest request
    ) {
        LogInRequest logInRequest = new LogInRequest();
        Cookie[] cookies = request.getCookies();
        String loginResponse = cookieUtil.getCookie(cookies, "loginFailure");

        if(!loginResponse.isEmpty()){
            model.addAttribute("alertType", SystemConstant.ALERT_DANGER);
            model.addAttribute("message", loginResponse);
        }
        model.addAttribute("account", logInRequest);
        return "authenticate/login";
    }
    
    @GetMapping("/register")
    public String register(Model model) {
        RegisterRequest registerRequest = new RegisterRequest();
        model.addAttribute("account", registerRequest);
        return "authenticate/register";
    }

    @PostMapping("/register")
    public String save(
        @Valid @ModelAttribute("account") RegisterRequest request,
        BindingResult bindingResult,
        RedirectAttributes redirectAttributes
    ){
        
        if (request.getPassword() != null && request.getRePassword() != null) {
            if (!request.getPassword().equals(request.getRePassword())) {
                bindingResult.addError(new FieldError("account", "rePassword", "Password must match"));
            }
        }
         
        if (request.getUsername() != null) {
            boolean isAccountExistByUserName = accountService.isAccountExistByUsername(request.getUsername());
            if (isAccountExistByUserName) {
                bindingResult.addError(new FieldError("account", "userName", "The user name has existed, please choose another."));
            }
        }
        
        if (request.getEmail() != null) {
            boolean isAccountExistByEmail = accountService.isAccountExistByEmail(request.getEmail());
            if (isAccountExistByEmail) {
                bindingResult.addError(new FieldError("account", "email", "The email has existed, please choose another."));
            }
        }
        
        if (bindingResult.hasErrors()) {
            return "authenticate/register";
        }
        
        authenticationAPI.register(request);
        redirectAttributes.addFlashAttribute("message", "Success! Your registration is now complete.");
        redirectAttributes.addFlashAttribute("alertType", SystemConstant.ALERT_INFO);
        return "redirect:login";
    }
    
    
    @GetMapping(value = "/register-seller")
    public String registerSeller() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO accountDTO = accountService.findByUsername(username);
        
        AccountRoleDTO accountRoleDTO = new AccountRoleDTO();
        accountRoleDTO.setAccountEntity(accountConverter.toEntity(accountDTO));
        RoleEntity roleEntity = new RoleEntity(SystemConstant.ROLE_SELLER, "seller");
        accountRoleDTO.setRoleEntity(roleEntity);
        
        accountRoleService.save(accountRoleDTO);
        
        return "redirect:/seller/recentSales";
    }

}
