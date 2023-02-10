package com.ecommerce.springbootecommerce.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.converter.AccountConverter;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.AccountRoleDTO;
import com.ecommerce.springbootecommerce.entity.RoleEntity;
import com.ecommerce.springbootecommerce.service.IAccountRoleService;
import com.ecommerce.springbootecommerce.service.IAccountService;

@Controller
public class AuthenticateController {
    
    @Autowired
    private IAccountService accountService;
       
    @Autowired
    private IAccountRoleService accountRoleService;
    
    @Autowired
    private AccountConverter accountConverter;
    
    @GetMapping("/login")
    public String login() {
        return "authenticate/login";
    }
    
    @GetMapping("/register")
    public String register(Model model) {
        AccountDTO account = new AccountDTO();
        model.addAttribute("account", account);
        
        return "authenticate/register";
    }
	    
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
	
    @PostMapping("/register")
    public String save(@Valid @ModelAttribute("account") AccountDTO account,
            BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        
        if (account.getPassword() != null && account.getRePassword() != null) {
            if (!account.getPassword().equals(account.getRePassword())) {
                bindingResult.addError(new FieldError("account", "rePassword", "Password must match"));
            }
        }
         
        if (account.getUserName() != null) {
            boolean isAcountExistByUserName = accountService.isAccountExistByUserName(account.getUserName());
            if (isAcountExistByUserName) {
                bindingResult.addError(new FieldError("account", "userName", "The user name has existed, please choose another."));
            }
        }
        
        if (account.getEmail() != null) {
            boolean isAcountExistByEmail = accountService.isAccountExistByEmail(account.getEmail());
            if (isAcountExistByEmail) {
                bindingResult.addError(new FieldError("account", "email", "The email has existed, please choose another."));
            }
        }
        
        if (bindingResult.hasErrors()) {
            return "authenticate/register";
        }
        
        redirectAttributes.addFlashAttribute("message", "Success! Your registration is now complete.");
        accountService.register(account);
        return "redirect:login";
    }
    
    
    @GetMapping(value = "/register-seller")
    public String registerSeller() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO accountDTO = accountService.findByUserName(userName);
        
        AccountRoleDTO accountRoleDTO = new AccountRoleDTO();
        accountRoleDTO.setAccountEntity(accountConverter.toEntity(accountDTO));
        RoleEntity roleEntity = new RoleEntity(SystemConstant.ROLE_SELLER, "seller");
        accountRoleDTO.setRoleEntity(roleEntity);
        
        accountRoleService.save(accountRoleDTO);
        
        return "redirect:/seller/recentSales";
    }
}
