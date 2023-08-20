package com.ecommerce.springbootecommerce.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HandleErrorController implements ErrorController {
    @GetMapping(value = "/error")
    public String handleError(HttpServletResponse response) {

        return "/error/error";
    }
}
