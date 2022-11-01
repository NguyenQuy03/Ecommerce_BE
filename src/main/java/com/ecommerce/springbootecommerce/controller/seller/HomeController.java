package com.ecommerce.springbootecommerce.controller.seller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller(value = "homeControllerOfSeller")
@RequestMapping("seller")
public class HomeController {
	
	@GetMapping("recentSales")
	public String recentSalesPage() {
		return "seller/recentSales";
	}

	@GetMapping("topSelling")
	public String topSellingPage() {
		return "seller/topSelling";
	}
}
