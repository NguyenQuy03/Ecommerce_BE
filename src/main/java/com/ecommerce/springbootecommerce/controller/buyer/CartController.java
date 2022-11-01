package com.ecommerce.springbootecommerce.controller.buyer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CartController {
    
    @GetMapping(value="cart")
    public String cart() {
        return "buyer/cart";
    }
    
}
