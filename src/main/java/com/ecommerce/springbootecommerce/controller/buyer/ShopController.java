package com.ecommerce.springbootecommerce.controller.buyer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.service.IAccountService;

@Controller
public class ShopController {

    @Autowired
    private IAccountService accountService;
    
    @GetMapping("/shop/{shopName}")
    public String displayShop(
        Model model,
        @PathVariable("shopName") String shopName
    ) {
        AccountDTO dto = accountService.findByUsername(shopName);

        model.addAttribute("dto", dto);

        return "buyer/shop";
    }
}
