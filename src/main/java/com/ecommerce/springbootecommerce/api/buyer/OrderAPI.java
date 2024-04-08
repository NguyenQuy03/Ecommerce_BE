package com.ecommerce.springbootecommerce.api.buyer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.constant.enums.order.OrderStatus;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.service.IOrderService;
import com.ecommerce.springbootecommerce.service.impl.OrderService;
import com.ecommerce.springbootecommerce.util.AccountUtil;

@RestController
@RequestMapping("/api/v1/buyer/order")
public class OrderAPI {

    private final IOrderService orderService;

    private final AccountUtil accountUtil;

    public OrderAPI(OrderService orderService, AccountUtil accountUtil) {
        this.orderService = orderService;
        this.accountUtil = accountUtil;
    }

    @PostMapping()
    public ResponseEntity<String> purchase(
            @RequestBody OrderDTO orderDTO) {
        AccountDTO accountDTO = accountUtil.getCurAccount();

        // Check if user's information is not enough
        // if(accountDTO.getAddress().isEmpty() ||
        // accountDTO.getPhoneNumber().isEmpty()) {
        // }

        orderDTO.setBuyer(accountDTO);
        orderDTO.setStatus(OrderStatus.DELIVERED);

        try {
            orderService.save(orderDTO);
            return ResponseEntity.ok("Your order is delivered");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error add cart item");
        }

    }

}