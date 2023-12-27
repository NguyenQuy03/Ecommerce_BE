package com.ecommerce.springbootecommerce.controller.buyer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.springbootecommerce.service.IOrderService;


@Controller
@RequestMapping("/checkout")
public class CheckOutController {

    @Autowired
    private IOrderService orderService;

    // @Autowired
    // private ICartService cartService;

    // @Autowired
    // private RedisUtil redisUtil;
    
    // @PostMapping()
    // public String displayCheckOutForm(
    //     CartDTO dto,
    //     Model model
    // ) {
    //     CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    //     CartDTO cart = cartService.findOneByAccountId(userDetails.getId());

    //     List<OrderDTO> orders = orderService.findAllByCartIdAndStatus(cart.getId(), OrderStatus.PENDING);
    //     model.addAttribute(
    //         RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER,
    //         Integer.parseInt((String) redisUtil.getHashField(RedisConstant.REDIS_USER_INFO + userDetails.getUsername(),
    //         RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER))
    //     );
    //     model.addAttribute("orders", orders);
        
    //     return "buyer/checkout";

    // }
}
