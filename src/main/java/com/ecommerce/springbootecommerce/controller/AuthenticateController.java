package com.ecommerce.springbootecommerce.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.springbootecommerce.api.common.authenticate.AuthenticationAPI;
import com.ecommerce.springbootecommerce.api.common.authenticate.payload.request.RegisterRequest;
import com.ecommerce.springbootecommerce.constant.AlertConstant;
import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.util.CookieUtil;

@Controller
public class AuthenticateController {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private AuthenticationAPI authenticationAPI;

    @Autowired
    CookieUtil cookieUtil;

    @PostMapping("/register")
    public String save(
            @Valid @ModelAttribute(SystemConstant.ACCOUNT_DTO) RegisterRequest request,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (request.getPassword() != null && request.getConfirmPassword() != null
                && !request.getPassword().equals(request.getConfirmPassword())) {
            bindingResult.addError(
                    new FieldError(SystemConstant.ACCOUNT_DTO, "rePassword",
                            AlertConstant.ALERT_WRONG_CONFIRM_PASSWORD));
        }

        for (Character c : request.getUsername().toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                bindingResult.addError(
                        new FieldError(SystemConstant.ACCOUNT_DTO, "username", AlertConstant.ALERT_WRONG_REGEX));
                break;
            }
        }

        if (request.getUsername() != null && accountService.isAccountExistByUsername(request.getUsername())) {
            bindingResult.addError(
                    new FieldError(SystemConstant.ACCOUNT_DTO, "username", AlertConstant.ALERT_USERNAME_EXISTED));
        }

        if (request.getEmail() != null && accountService.isAccountExistByEmail(request.getEmail())) {
            bindingResult
                    .addError(new FieldError(SystemConstant.ACCOUNT_DTO, "email", AlertConstant.ALERT_EMAIL_EXISTED));
        }

        if (bindingResult.hasErrors()) {
            return "authenticate/register";
        }

        try {
            authenticationAPI.register(request);
        } catch (Exception e) {
            return "redirect:/error";
        }
        redirectAttributes.addFlashAttribute("message", AlertConstant.ALERT_SUCCESS_REGISTATION);
        redirectAttributes.addFlashAttribute("alertType", AlertConstant.ALERT_INFO);
        return "redirect:login";
    }

    @GetMapping(value = "/register-seller")
    public String registerSeller() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO accountDTO = accountService.findByUsername(username);

        accountService.registerSeller(accountDTO);

        return "redirect:/seller/recentSales";
    }

}
