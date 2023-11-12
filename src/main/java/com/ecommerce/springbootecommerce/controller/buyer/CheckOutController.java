package com.ecommerce.springbootecommerce.controller.buyer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.springbootecommerce.constant.RedisConstant;
import com.ecommerce.springbootecommerce.constant.enums.order.OrderStatus;
import com.ecommerce.springbootecommerce.dto.CartDTO;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.service.ICartService;
import com.ecommerce.springbootecommerce.service.IOrderService;
import com.ecommerce.springbootecommerce.util.RedisUtil;


@Controller
@RequestMapping("/checkout")
public class CheckOutController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ICartService cartService;

    @Autowired
    private RedisUtil redisUtil;
    
    @PostMapping()
    public String displayCheckOutForm(
        CartDTO dto,
        Model model
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CartDTO cart = cartService.findOneByAccountId(userDetails.getId());

        List<OrderDTO> orders = orderService.findAllByCartIdAndStatus(cart.getId(), OrderStatus.PENDING);
        model.addAttribute(
            RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER,
            Integer.parseInt((String) redisUtil.getHashField(RedisConstant.REDIS_USER_INFO + userDetails.getUsername(),
            RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER))
        );
        model.addAttribute("orders", orders);
        
        return "buyer/checkout";

    }
}
