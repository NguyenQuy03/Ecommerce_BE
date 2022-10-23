package com.ecommerce.springbootecommerce.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller(value = "homeControllerOfUser")
public class HomeController {

	@GetMapping("home")
	public String homePage() {
		return "user/home";
	}

	@GetMapping("login")
	public String login() {
		return "user/login";
	}

	@GetMapping("logout")
	public String logout() {
		return "user/login";
	}

	@GetMapping("register")
	public String register() {
		return "user/register";
	}
}
