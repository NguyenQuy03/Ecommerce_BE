package com.ecommerce.springbootecommerce.controller.buyer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/buyer/detail")
public class DetailProductController {
    
    @GetMapping("/product1")
    public String displayProduct() {
        
        return "/buyer/detailProduct";
    }
}
