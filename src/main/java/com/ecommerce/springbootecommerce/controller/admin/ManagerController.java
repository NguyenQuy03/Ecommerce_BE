package com.ecommerce.springbootecommerce.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/manager")
public class ManagerController {
    
    @GetMapping(value="/home")
    public String home() {
        return "admin/manager/home";
    }
    
}
