package com.ecommerce.springbootecommerce.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/account")
public class AccountController {
    @GetMapping(value="buyerAccount")
    public String buyerAccount() {
        return "admin/accountAdmin/buyerAccount";
    }

    @GetMapping(value="sellerAccount")
    public String sellerAccount() {
        return "admin/accountAdmin/sellerAccount";
    }  
}
