package com.ecommerce.springbootecommerce.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller(value = "homeControllerOfAdmin")
@RequestMapping("admin")
public class HomeController {

	@GetMapping("home")
	public String homePage() {
		return "admin/home";
	}
}
