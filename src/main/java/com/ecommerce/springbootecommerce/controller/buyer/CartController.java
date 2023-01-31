package com.ecommerce.springbootecommerce.controller.buyer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.CartDTO;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.service.ICartService;
import com.ecommerce.springbootecommerce.service.IOrderService;
import com.ecommerce.springbootecommerce.util.QuantityOrderUtil;


@Controller
public class CartController {
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private ICartService cartService;
    
    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private QuantityOrderUtil quantityOrderUtil;
    
    @GetMapping(value="cart")
    public String cart(
            Model model
    ) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO accountDTO = accountService.findAccountByUserName(userName);
        
        Boolean isCartExist = cartService.isExistByStatusAndAccountId(SystemConstant.STRING_ACTIVE_STATUS, accountDTO.getId());
        CartDTO cartDTO = new CartDTO();
        
        if (isCartExist) {
            cartDTO = cartService.findByStatusAndAccountId(SystemConstant.STRING_ACTIVE_STATUS, accountDTO.getId());
        } else {
            cartDTO.setStatus(SystemConstant.STRING_ACTIVE_STATUS);
            
            cartService.save(cartDTO);
        }
                
        List<OrderDTO> listOrder = orderService.findAllByCartIdAndStatus(cartDTO.getId(), SystemConstant.STRING_ACTIVE_STATUS);
        model.addAttribute("quantityOrder",quantityOrderUtil.getQuantityOrder());
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("cartId", cartDTO.getId());
        
        return "buyer/cart";
    }
    
}
