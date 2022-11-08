package com.ecommerce.springbootecommerce.controller.seller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.springbootecommerce.dto.ProductDTO;

@Controller
@RequestMapping("seller/product")
public class ProductController {
    
	@GetMapping("list")
	public String listProduct() {
		return "seller/product/myProduct";
	}

	@GetMapping("new")
	public String newProduct(Model model) {
	    model.addAttribute("product", new ProductDTO());
		return "seller/product/newProduct";
	}
}
