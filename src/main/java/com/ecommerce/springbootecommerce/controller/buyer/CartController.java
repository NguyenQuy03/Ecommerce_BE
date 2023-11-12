package com.ecommerce.springbootecommerce.controller.buyer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.springbootecommerce.constant.RedisConstant;
import com.ecommerce.springbootecommerce.constant.StatusConstant;
import com.ecommerce.springbootecommerce.constant.enums.order.OrderStatus;
import com.ecommerce.springbootecommerce.dto.CartDTO;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.dto.OrderItemDTO;
import com.ecommerce.springbootecommerce.service.ICartService;
import com.ecommerce.springbootecommerce.service.IOrderService;
import com.ecommerce.springbootecommerce.service.IVoucherService;
import com.ecommerce.springbootecommerce.util.RedisUtil;


@Controller
public class CartController {
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private ICartService cartService;

    @Autowired
    private IVoucherService voucherService;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @GetMapping(value="cart")
    public String displayCart(
            Model model
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CartDTO cart = cartService.findOneByAccountId(userDetails.getId());

        List<OrderDTO> orders = orderService.findAllByCartIdAndStatus(cart.getId(), OrderStatus.PENDING);

        Map<String, String> map = new HashMap<>();

        for(OrderDTO order : orders) {
            // List<VoucherDTO> vouchers = voucherService.findAllByAccountIdAndStatus(order.getAccount().getId(), StatusConstant.STRING_ACTIVE_STATUS);
        }

        // Handle Inactive Product
        if(orders != null) {
            List<Long> inActiveCartItem = new ArrayList<>();
            List<String> inActiveStatus = Arrays.asList(StatusConstant.REMOVED_STATUS, StatusConstant.STRING_INACTIVE_STATUS, StatusConstant.SOLD_OUT_STATUS);
            for(OrderDTO order : orders) {
                for(OrderItemDTO orderItem : order.getOrderItems()) {
                    String productStatus = orderItem.getProductItem().getStatus();

                    if (inActiveStatus.contains(productStatus)) {
                        inActiveCartItem.add(orderItem.getProductItem().getId());
                    }
                }
            }
            model.addAttribute("inActiveCartItem", inActiveCartItem);
        }

        model.addAttribute(
            RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER,
            Integer.parseInt((String) redisUtil.getHashField(RedisConstant.REDIS_USER_INFO + userDetails.getUsername(),
            RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER))
        );

        model.addAttribute("orders", orders);
        
        return "buyer/cart";
    }
    
    @GetMapping("/purchase")
    public String purchase(
            Model model,
            @RequestParam(value="page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value="size", required = false, defaultValue = "5") Integer size
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<OrderDTO> orders = orderService.findAllByAccountIdAndStatus(userDetails.getId(), OrderStatus.DELIVERED, page, size);
        
        Integer totalPage = (int) Math.ceil((double) orders.size() / size);

        OrderDTO dto = new OrderDTO();
        dto.setTotalPage(totalPage);
        dto.setListResult(orders);
        dto.setPage(page);
        dto.setSize(size);

        model.addAttribute("dto", dto);
        model.addAttribute(
            RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER,
            Integer.parseInt((String) redisUtil.getHashField(RedisConstant.REDIS_USER_INFO + userDetails.getUsername(),
            RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER))
        );
        return "buyer/purchase";
    }
}
