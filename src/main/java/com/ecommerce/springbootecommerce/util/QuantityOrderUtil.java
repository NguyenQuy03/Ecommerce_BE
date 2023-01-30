package com.ecommerce.springbootecommerce.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.converter.AccountConverter;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.CartDTO;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.service.ICartService;
import com.ecommerce.springbootecommerce.service.IOrderService;

@Component
public class QuantityOrderUtil {
    @Autowired
    private IOrderService orderService;

    @Autowired
    private ICartService cartService;
    
    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private AccountConverter accountConverter;

    public Long getQuantityOrder() {

        Long quantityOrder = 0L;
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        
        if (!userName.contains("anonymousUser")) {
            AccountDTO accountDTO = accountService.findAccountByUserName(userName);
            boolean isCartExist = cartService.isExistByStatusAndAccountId(SystemConstant.STRING_ACTIVE_STATUS, accountDTO.getId());
            CartDTO cartDTO = new CartDTO();
            
            if (isCartExist) {
                cartDTO = cartService.findByStatusAndAccountId(SystemConstant.STRING_ACTIVE_STATUS, accountDTO.getId());
                quantityOrder = orderService.countByCartId(cartDTO.getId());
            } else {
                cartDTO.setAccount(accountConverter.toEntity(accountDTO));
                cartDTO.setStatus(SystemConstant.STRING_ACTIVE_STATUS);
                cartService.save(cartDTO);
            }
        }

        return quantityOrder;
    }

}
