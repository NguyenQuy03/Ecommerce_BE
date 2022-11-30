package com.ecommerce.springbootecommerce.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.service.IAccountService;

@Controller
public class AuthenticateController {
    
    @Autowired
    private IAccountService accountService;
    
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
            BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        
        if (account.getPassword() != null && account.getRePassword() != null) {
            if (!account.getPassword().equals(account.getRePassword())) {
                bindingResult.addError(new FieldError("account", "rePassword", "Password must match"));
            }
        }
        
        if (account.getUserName() != null) {
            List<String> listUserName = accountService.findAllUserName();
            for (String userName : listUserName) {
                if (account.getUserName().equals(userName)) {
                    bindingResult.addError(new FieldError("account", "userName", "The user name has existed, please choose another."));
                }
            }
        }
        
        if (account.getEmail() != null) {
            List<String> listEmail = accountService.findAllEmail();
            for (String email : listEmail) {
                if (account.getEmail().equals(email)) {
                    bindingResult.addError(new FieldError("account", "email", "The email has existed, please choose another."));
                }
            }
        }
        
        if (bindingResult.hasErrors()) {
            return "authenticate/register";
        }
        
        redirectAttributes.addFlashAttribute("message", "Success! Your registration is now complete.");
        accountService.register(account);
        return "redirect:login";
    }
}
