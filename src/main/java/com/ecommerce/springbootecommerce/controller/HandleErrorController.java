package com.ecommerce.springbootecommerce.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

@Controller
public class HandleErrorController implements ErrorController {
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String handleError(HttpServletResponse response) {

        return "/error/error";
    }
}
