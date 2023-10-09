package com.ecommerce.springbootecommerce.controller.buyer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.springbootecommerce.constant.RedisConstant;
import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.CartDTO;
import com.ecommerce.springbootecommerce.dto.CartItemDTO;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.service.ICartItemService;
import com.ecommerce.springbootecommerce.service.ICartService;
import com.ecommerce.springbootecommerce.service.IOrderService;
import com.ecommerce.springbootecommerce.util.RedisUtil;


@Controller
public class CartController {
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ICartService cartService;

    @Autowired
    private ICartItemService cartItemService;
    
    @GetMapping(value="cart")
    public String cart(
            Model model
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CartDTO cart = cartService.findOneByAccountId(userDetails.getId());

        List<CartItemDTO> cartItems = cartItemService.findAllByCartId(cart.getId());
        
        // StringBuilder productNotAvailable = new StringBuilder();
        
        // if(cartItems != null) {
        //     for(CartItemDTO item : cartItems) {
        //         String productStatus = item.getProductItem().getStatus();

        //         if (productStatus.equals(SystemConstant.REMOVED_PRODUCT)
        //                 || productStatus.equals(SystemConstant.INACTIVE_PRODUCT)
        //                 || productStatus.equals(SystemConstant.SOLD_OUT_PRODUCT))
        //         {
        //             productNotAvailable.append(item.getProductItem().getProductId() + ", ");
        //             cartItems.remove(item);
        //             cartItemService.delete(item.getId());
        //         }
        //     }
        // }
        
        // if (!productNotAvailable.isEmpty()) {
        //     String s = SystemConstant.PRODUCT_NOT_AVAILABLE + ": " + productNotAvailable.substring(0, productNotAvailable.length() - 2);
        //     model.addAttribute("message", s);
        // }

        model.addAttribute(RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER, Integer.parseInt((String) redisUtil.getHashField(RedisConstant.REDIS_USER_INFO + userDetails.getUsername(), RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER)) );
        model.addAttribute("cartItems", cartItems);
        
        return "buyer/cart";
    }
    
    @GetMapping("/purchase")
    public String purchase(
            Model model,
            @RequestParam(value="page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value="size", required = false, defaultValue = "5") Integer size
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<OrderDTO> orders = orderService.findAllByAccountIdAndStatus(userDetails.getId(), SystemConstant.DELIVERED_STATUS, page, size);
        
        long quantityOrder = orders.size();

        Integer totalPage = (int) Math.ceil((double) quantityOrder / size);

        if(quantityOrder == 0) {
            model.addAttribute("notOrderYet", true);
        }

        OrderDTO dto = new OrderDTO();
        dto.setTotalPage(totalPage);
        dto.setListResult(orders);
        dto.setPage(page);
        dto.setSize(size);

        model.addAttribute("dto", dto);
        model.addAttribute(RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER, Integer.parseInt((String) redisUtil.getHashField(RedisConstant.REDIS_USER_INFO + userDetails.getUsername(), RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER)) );

        return "buyer/purchase";
    }
}
