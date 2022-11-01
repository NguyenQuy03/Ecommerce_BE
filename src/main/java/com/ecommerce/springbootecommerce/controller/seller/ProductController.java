package com.ecommerce.springbootecommerce.controller.seller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("product")
public class ProductController {
    
	@GetMapping("list")
	public String listProduct() {
		return "seller/product/myProduct";
	}

	@GetMapping("new")
	public String newProduct() {
		return "seller/product/newProduct";
	}
}
