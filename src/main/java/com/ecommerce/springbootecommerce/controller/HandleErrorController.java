package com.ecommerce.springbootecommerce.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HandleErrorController implements ErrorController {
    
    @RequestMapping("/error")
    public String handleError() {
        
        return "/error/error";
    }
}
