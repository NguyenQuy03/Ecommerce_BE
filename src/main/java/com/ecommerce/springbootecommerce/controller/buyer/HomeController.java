package com.ecommerce.springbootecommerce.controller.buyer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller(value = "homeControllerOfBuyer")
public class HomeController {

	@GetMapping("home")
	public String homePage() {
		return "buyer/home";
	}
}
