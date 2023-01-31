package com.ecommerce.springbootecommerce.api.buyer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.converter.OrderConverter;
import com.ecommerce.springbootecommerce.dto.CartDTO;
import com.ecommerce.springbootecommerce.service.ICartService;
import com.ecommerce.springbootecommerce.service.IOrderService;
import com.ecommerce.springbootecommerce.service.IProductService;

@RestController
@RequestMapping("/api/buyer/cart")
public class CartAPI {

    @Autowired
    private ICartService cartService;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private OrderConverter orderConverter;
    
    @Autowired
    private IProductService productService;
    
    @PostMapping()
    public void purchase(
            @RequestBody CartDTO cartDTO) {

        CartDTO preCart = cartService.findOneById(cartDTO.getId());
        preCart.getSetOrders().forEach(order -> {
            for (long id : cartDTO.getIds()) {
                if (order.getId() == id){
                    order.setStatus(SystemConstant.STRING_DELIVERIED_ORDER);
                    orderService.save(orderConverter.toDTO(order));
                    
                    order.getProduct().setSold(order.getProduct().getSold() + order.getQuantity());
                    productService.save(order.getProduct());
                }
            }
        });

    }
}
