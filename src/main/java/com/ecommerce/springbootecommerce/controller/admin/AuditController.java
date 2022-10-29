package com.ecommerce.springbootecommerce.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/audit")
public class AuditController {

    @GetMapping(value="recentTransaction")
    public String recentTransaction() {
        return "admin/auditAdmin/recentTransaction";
    }

    @GetMapping(value="topSelling")
    public String topSelling() {
        return "admin/auditAdmin/topSelling";
    }  
}
