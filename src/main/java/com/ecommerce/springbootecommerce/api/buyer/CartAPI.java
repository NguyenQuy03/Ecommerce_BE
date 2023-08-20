package com.ecommerce.springbootecommerce.api.buyer;

import com.ecommerce.springbootecommerce.dto.CartDTO;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.service.ICartItemService;
import com.ecommerce.springbootecommerce.service.ICartService;
import com.ecommerce.springbootecommerce.service.IOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/buyer/cart")
public class CartAPI {

    @Autowired
    private ICartService cartService;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ICartItemService cartItemService;
    
    @PostMapping()
    public void purchase(
            @RequestBody CartDTO dto
    ) {
//        CartDTO cart = cartService.findOneById(dto.getId());
//
//        for(OrderDTO order : dto.getSetOrders()) {
//            orderService.purchase(order);
//            cart.getSetOrders().removeIf(element -> element.getId().equals(order.getId()));
//        }
    }
}
