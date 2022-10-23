package com.ecommerce.springbootecommerce.controller.seller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller(value = "homeControllerOfSeller")
@RequestMapping("seller")
public class HomeController {
	
	@GetMapping("home")
	public String homePage() {
		return "seller/home";
	}
}
