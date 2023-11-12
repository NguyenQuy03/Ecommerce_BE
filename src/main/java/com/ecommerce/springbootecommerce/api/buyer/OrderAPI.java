package com.ecommerce.springbootecommerce.api.buyer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.constant.enums.order.OrderStatus;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.service.IOrderService;

@RestController
@RequestMapping("/api/buyer/order")
public class OrderAPI {
    
    @Autowired
    private IOrderService orderService;

    @Autowired
    private IAccountService accountService;
    
    @PostMapping()
    public ResponseEntity<String> purchase(
            @RequestBody OrderDTO orderDTO
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccountDTO accountDTO = accountService.findById(userDetails.getId());

        // Check if user's information is not enough
        // if(accountDTO.getAddress().isEmpty() || accountDTO.getPhoneNumber().isEmpty()) {
        // }

        orderDTO.setAccount(accountDTO);
        orderDTO.setStatus(OrderStatus.DELIVERED);

        try {
            orderService.save(orderDTO);
            return ResponseEntity.ok("Your order is delivered");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error add cart item");
        }
        
    }

}