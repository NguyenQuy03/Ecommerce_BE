package com.ecommerce.springbootecommerce.controller;


import javax.validation.Valid;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ecommerce.springbootecommerce.dto.AccountDTO;

@Controller
public class AuthenticateController {
    
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
    
	@GetMapping("/profile")
	public String profile() {
		return "authenticate/profile";
	}	
	    
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
	
    @PostMapping("/register")
    public String save(@Valid @ModelAttribute("account") AccountDTO account,
            BindingResult bindingResult, Model model) {
        
        if (bindingResult.hasErrors()) {
            return "authenticate/register";
        }
        
        if (account.getPassword() != null && account.getRePassword() != null) {
            if (!account.getPassword().equals(account.getRePassword())) {
                bindingResult.addError(new FieldError("account", "rePassword", "Password must match"));
            }
        }
        
        return "redirect:login";
    }
}
