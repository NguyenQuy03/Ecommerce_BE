package com.ecommerce.springbootecommerce.controller.buyer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.service.IOrderService;


@Controller
public class CartController {
    
    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private IOrderService orderService;
    
    @GetMapping(value="cart")
    public String cart(
            Model model
    ) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO accountDTO = accountService.findAccountByUserName(userName);
        if (!userName.contains("anonymousUser")) {
            Long quantityOrder = orderService.countByAccountId(accountDTO.getId());
            
            model.addAttribute("quantityOrder", quantityOrder);
        }
        
        List<OrderDTO> listOrder = orderService.findAllByAccountId(accountDTO.getId());
                
        model.addAttribute("listOrder", listOrder);
        
        return "buyer/cart";
    }
    
    
}
