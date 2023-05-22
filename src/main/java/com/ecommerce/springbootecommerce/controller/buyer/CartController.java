package com.ecommerce.springbootecommerce.controller.buyer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.CartDTO;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.service.ICartService;
import com.ecommerce.springbootecommerce.service.IOrderService;
import com.ecommerce.springbootecommerce.util.QuantityOrderUtil;


@Controller
public class CartController {
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private ICartService cartService;
    
    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private QuantityOrderUtil quantityOrderUtil;
    
    @GetMapping(value="cart")
    public String cart(
            Model model
    ) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO accountDTO = accountService.findByUserName(userName);
        
        Boolean isCartExist = cartService.isExistByStatusAndAccountId(SystemConstant.STRING_ACTIVE_STATUS, accountDTO.getId());
        CartDTO cartDTO = new CartDTO();
        
        if (isCartExist) {
            cartDTO = cartService.findByStatusAndAccountId(SystemConstant.STRING_ACTIVE_STATUS, accountDTO.getId());
        } else {
            cartDTO.setStatus(SystemConstant.STRING_ACTIVE_STATUS);
            cartService.save(cartDTO);
        }
                
        List<OrderDTO> listOrder = orderService.findAllByCartIdAndStatus(cartDTO.getId(), SystemConstant.STRING_ACTIVE_STATUS);
        
        StringBuilder productNotAvailable = new StringBuilder();
        
        if(!listOrder.isEmpty()) {
            int n = listOrder.size();
            for (int i = 0; i < n; i++) {
                OrderDTO order = listOrder.get(i);
                String productStatus = order.getProduct().getStatus();
                
                if (productStatus.equals(SystemConstant.REMOVED_PRODUCT) 
                        || productStatus.equals(SystemConstant.DELETED_PRODUCT)
                        || productStatus.equals(SystemConstant.SOLD_OUT_PRODUCT))
                {
                    productNotAvailable.append(order.getProduct().getName() + ", ");
                    listOrder.remove(order);
                    orderService.delete(order.getId());
                }
            }
        }
        
        if (!productNotAvailable.isEmpty()) {
            String s = SystemConstant.PRODUCT_NOT_AVAILABLE + ": " + productNotAvailable.substring(0, productNotAvailable.length() - 2);
            model.addAttribute("message", s);
        }
        
        model.addAttribute("quantityOrder",quantityOrderUtil.getQuantityOrder());
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("cartId", cartDTO.getId());
        
        return "buyer/cart";
    }
    
    @GetMapping("/purchase")
    public String purchase(
            Model model,
            @RequestParam(value="page", required = false) Integer page,
            @RequestParam(value="size", required = false) Integer size
    ) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO accountDTO = accountService.findByUserName(userName);
        
        CartDTO cartDTO = cartService.findByStatusAndAccountId(SystemConstant.STRING_ACTIVE_STATUS, accountDTO.getId());
        Long quantityOrder = orderService.countByCartIdAndStatus(cartDTO.getId(), SystemConstant.STRING_DELIVERED_ORDER);
        
        if (page == null && size == null) {
            page = 1;
            size = 5;
        }
        @SuppressWarnings("null")
        Pageable pageable = PageRequest.of(page - 1, size);
        Integer totalPage = (int) Math.ceil((double) quantityOrder / size);
        
        List<OrderDTO> listOrder = orderService.findAllByCartIdAndStatus(cartDTO.getId(), SystemConstant.STRING_DELIVERED_ORDER, pageable);

        OrderDTO dto = new OrderDTO();
        dto.setTotalPage(totalPage);
        dto.setListResult(listOrder);
        dto.setPage(page);
        dto.setSize(size);

        model.addAttribute("dto", dto);
        model.addAttribute("quantityOrder", quantityOrderUtil.getQuantityOrder());
        model.addAttribute("cartId", cartDTO.getId());
                
        return "buyer/purchase";
    }
    
    @PostMapping("/buy-again")
    public String redirectToCart(
            Model model,
            @RequestParam("orderId") Long orderId,
            @RequestParam("cartId") Long cartId,
            RedirectAttributes redirectAttributes
            
    ) {
        OrderDTO orderDTO = orderService.findOneById(orderId);
        
        boolean isOrderExisted = orderService.isOrderExistByProductIdAndCartIdAndStatus(orderDTO.getProduct().getId(), cartId, SystemConstant.STRING_ACTIVE_STATUS);
        
        String productStatus = orderDTO.getProduct().getStatus();
        if (productStatus.equals(SystemConstant.DELETED_PRODUCT) || productStatus.equals(SystemConstant.REMOVED_PRODUCT)) {
            redirectAttributes.addFlashAttribute("message", SystemConstant.PRODUCT_NOT_AVAILABLE);
            return "redirect:/purchase";
        }
        
        if (isOrderExisted) {
            OrderDTO existedDTO = orderService.findOneByProductIdAndCartIdAndStatus(orderDTO.getProduct().getId(), cartId, SystemConstant.STRING_ACTIVE_STATUS);
            existedDTO.setQuantity(1 + existedDTO.getQuantity());
            orderService.save(existedDTO);
        } else {
            if (orderDTO.getProduct().getStock() > 0) {
                orderDTO.setId(null);
                orderDTO.setStatus(SystemConstant.STRING_ACTIVE_STATUS);
                orderDTO.setQuantity(1L);
                
                orderService.save(orderDTO);
            }
        }
        return "redirect:/cart";
    }
}
