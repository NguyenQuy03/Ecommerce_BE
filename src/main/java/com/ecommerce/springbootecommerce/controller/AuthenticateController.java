package com.ecommerce.springbootecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticateController {
    
    @GetMapping("/login")
    public String login() {
        return "authenticate/login";
    }
    
    @GetMapping("/register")
    public String register() {
        return "authenticate/register";
    }
    
	@GetMapping("profile")
	public String profile() {
		return "authenticate/profile";
	}
}
