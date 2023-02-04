package com.ecommerce.springbootecommerce.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class TransactionController {

    @GetMapping(value="/transaction")
    public String transactions() {
        return "manager/transactions/transactions";
    }
    
    @GetMapping(value="/topSelling")
    public String topSelling() {
        return "manager/transactions/topSelling";
    } 
}
