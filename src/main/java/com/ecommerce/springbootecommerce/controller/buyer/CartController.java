package com.ecommerce.springbootecommerce.controller.buyer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.springbootecommerce.constant.RedisConstant;
import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.CartDTO;
import com.ecommerce.springbootecommerce.dto.CartItemDTO;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.dto.OrderItemDTO;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.service.ICartItemService;
import com.ecommerce.springbootecommerce.service.ICartService;
import com.ecommerce.springbootecommerce.service.IOrderItemService;
import com.ecommerce.springbootecommerce.service.IOrderService;
import com.ecommerce.springbootecommerce.util.RedisUtil;


@Controller
public class CartController {
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private IAccountService accountService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IOrderItemService orderItemService;

    @Autowired
    private ICartService cartService;

    @Autowired
    private ICartItemService cartItemService;
    
    @GetMapping(value="cart")
    public String cart(
            Model model
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO accountDTO = accountService.findByUsername(username);

        CartDTO cart = cartService.findOneByAccountId(accountDTO.getId());
        //List<CartItemDTO> cartItems = cartItemService.findAllByCartId(cart.getId());
        List<CartItemDTO> cartItems = cart.getCartItems();
        StringBuilder productNotAvailable = new StringBuilder();
        
        if(cartItems != null) {
            for(CartItemDTO item : cartItems) {
                String productStatus = item.getProductItem().getStatus();

                if (productStatus.equals(SystemConstant.REMOVED_PRODUCT)
                        || productStatus.equals(SystemConstant.INACTIVE_PRODUCT)
                        || productStatus.equals(SystemConstant.SOLD_OUT_PRODUCT))
                {
                    productNotAvailable.append(item.getProductItem().getProductId() + ", ");
                    cartItems.remove(item);
                    cartItemService.delete(item.getId());
                }
            }
        }
        
        if (!productNotAvailable.isEmpty()) {
            String s = SystemConstant.PRODUCT_NOT_AVAILABLE + ": " + productNotAvailable.substring(0, productNotAvailable.length() - 2);
            model.addAttribute("message", s);
        }

        model.addAttribute(RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER, Integer.parseInt((String) redisUtil.getHashField(RedisConstant.REDIS_USER_INFO + username, RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER)) );
        model.addAttribute("cartItems", cartItems);
        //model.addAttribute("orderId", order.getId());
        
        return "buyer/cart";
    }
    
    @GetMapping("/purchase")
    public String purchase(
            Model model,
            @RequestParam(value="page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value="size", required = false, defaultValue = "5") Integer size
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO accountDTO = accountService.findByUsername(username);

        List<OrderDTO> orders = orderService.findAllByAccountIdAndStatus(accountDTO.getId(), SystemConstant.STRING_DELIVERED_ORDER);
        List<OrderItemDTO> orderItems = new ArrayList<>();
        orders.forEach(order ->{
            orderItems.addAll(order.getOrderItems());
        });

        long quantityOrder = orders.size();

        Integer totalPage = (int) Math.ceil((double) quantityOrder / size);

        if(quantityOrder == 0) {
            model.addAttribute("noOrderYet", true);
        }

        OrderItemDTO dto = new OrderItemDTO();
        dto.setTotalPage(totalPage);
        dto.setListResult(orderItems);
        dto.setPage(page);
        dto.setSize(size);

        model.addAttribute("dto", dto);
        model.addAttribute(RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER, Integer.parseInt((String) redisUtil.getHashField(RedisConstant.REDIS_USER_INFO + username, RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER)) );

        return "buyer/purchase";
    }
    
    @PostMapping("/buy-again")
    public String redirectToCart(
            Model model,
            @RequestParam("orderItemId") String orderItemId,
            RedirectAttributes redirectAttributes
            
    ) {
        OrderItemDTO orderItemDTO = orderItemService.findOneById(orderItemId);

        /* HANDLE IF PRODUCT ITEM IS NOT AVAILABLE */
        String productItemStatus = orderItemDTO.getProductItem().getStatus();
        if (productItemStatus.equals(SystemConstant.INACTIVE_PRODUCT) || productItemStatus.equals(SystemConstant.REMOVED_PRODUCT)) {
            redirectAttributes.addFlashAttribute("message", SystemConstant.PRODUCT_NOT_AVAILABLE);
            return "redirect:/purchase";
        }

        boolean isOrderItemExisted = orderItemService.isExistedByProductItemId(orderItemDTO.getProductItem().getId());
        if (isOrderItemExisted) {
            orderItemDTO.setQuantity(1 + orderItemDTO.getQuantity());
            orderItemService.save(orderItemDTO);
        } else {
            orderItemDTO.setId(null);
            orderItemDTO.setQuantity(1L);
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO accountDTO = accountService.findByUsername(username);
        CartDTO cart = cartService.findOneByAccountId(accountDTO.getId());
        CartItemDTO cartItem = CartItemDTO.builder()
                .cartId(cart.getId())
                .productItem(orderItemDTO.getProductItem())
                .quantity(1L)
                .build();

        cartItemService.save(cartItem);
        orderItemService.save(orderItemDTO);

        return "redirect:/cart";
    }
}
