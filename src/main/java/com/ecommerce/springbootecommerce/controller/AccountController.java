package com.ecommerce.springbootecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.springbootecommerce.config.AuthenticationConfig;
import com.ecommerce.springbootecommerce.constant.AlertConstant;
import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.service.IAccountService;

@Controller(value = "AccountController")
@RequestMapping("/profile")
public class AccountController {
    
    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private AuthenticationConfig authenticationConfig;

    @GetMapping()
    public String profile(Model model) {
        
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        
        boolean isOnlyBuyerRole = false;
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().size() == 1 && SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString().contains(SystemConstant.ROLE_BUYER)) {
            isOnlyBuyerRole = true;
        }
        AccountDTO account = accountService.findByUsername(username);
        account.setPassword("");
         
        model.addAttribute("account" , account);
        model.addAttribute("isOnlyBuyerRole", isOnlyBuyerRole);
        return "authenticate/profile";
    }
    
    @PostMapping()
    public String updateProfile(
            Model model, AccountDTO account,
            RedirectAttributes redirectAttributes
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO preAccount = accountService.findByUsername(username);
        if (account.getTypeEditProfile().equals("inforForm")) {
            account.setPassword(preAccount.getPassword());
            
        } else if (account.getTypeEditProfile().equals("passwordForm")) {
            String passwordEncoded = authenticationConfig.passwordEncoder().encode(account.getNewPassword());
            if (!bCryptPasswordEncoder.matches(account.getPassword(), preAccount.getPassword())) {
                return handleRedirectError(redirectAttributes, "Failure! Your password is incorrect.", AlertConstant.ALERT_DANGER);
            }
            if (account.getPassword().equals(account.getNewPassword())) {
                return handleRedirectError(redirectAttributes, "Failure! Your new password must be different from your old password.", AlertConstant.ALERT_DANGER);
            }
            if (account.getNewPassword() != null && account.getReNewPassword() != null && !account.getNewPassword().equals(account.getReNewPassword())) {
                    return handleRedirectError(redirectAttributes,"Failure! Your new password must match.", AlertConstant.ALERT_DANGER);
            }

            account.setPassword(passwordEncoded);
        }

        account.setId(preAccount.getId());
        accountService.update(account);

        return handleRedirectError(redirectAttributes, "Success! Your profile was updated.", AlertConstant.ALERT_INFO);
    }

    private String handleRedirectError(RedirectAttributes redirectAttributes, String message, String alertType) {
        redirectAttributes.addFlashAttribute("alertType", alertType);
        redirectAttributes.addFlashAttribute("alertMessage", message);
        return "redirect:/profile";
    }

}
