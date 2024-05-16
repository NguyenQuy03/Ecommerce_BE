package com.ecommerce.springbootecommerce.controller.buyer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecommerce.springbootecommerce.constant.RedisConstant;
import com.ecommerce.springbootecommerce.constant.enums.order.OrderStatus;
import com.ecommerce.springbootecommerce.constant.enums.product.ProductStatus;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.dto.OrderItemDTO;
import com.ecommerce.springbootecommerce.service.IOrderService;
import com.ecommerce.springbootecommerce.util.RedisUtil;

@Controller
public class CartController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping(value = "cart")
    public String displayCart(
            Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        List<OrderDTO> orders = orderService.findAllByBuyerIdAndStatus(userDetails.getId(), OrderStatus.PENDING);

        // Handle Inactive Product
        if (orders != null) {
            List<Long> inActiveCartItem = new ArrayList<>();
            List<ProductStatus> inActiveStatus = Arrays.asList(ProductStatus.REMOVED, ProductStatus.INACTIVE,
                    ProductStatus.SOLD_OUT);
            for (OrderDTO order : orders) {
                for (OrderItemDTO orderItem : order.getOrderItems()) {
                    ProductStatus productStatus = orderItem.getProductItem().getStatus();

                    if (inActiveStatus.contains(productStatus)) {
                        inActiveCartItem.add(orderItem.getProductItem().getId());
                    }
                }
            }
            model.addAttribute("inActiveCartItem", inActiveCartItem);
        }

        model.addAttribute(
                RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER,
                Integer.parseInt(
                        (String) redisUtil.getHashField(RedisConstant.REDIS_USER_INFO + userDetails.getUsername(),
                                RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER)));

        model.addAttribute("orders", orders);

        return "buyer/cart";
    }
}
