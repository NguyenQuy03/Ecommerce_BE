package com.ecommerce.springbootecommerce.api.buyer;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.CartDTO;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.service.ICartService;
import com.ecommerce.springbootecommerce.service.IOrderService;
import com.ecommerce.springbootecommerce.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/buyer/order")
public class OrderAPI {

    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private IProductService productService;
    
    @Autowired
    private ICartService cartService;
    
    @PostMapping()
    public RedirectView add(
            @RequestParam("id") String productId,
            @RequestParam("quantity") Long quantity
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO accountDTO = accountService.findByUsername(username);
        CartDTO cartDTO = cartService.findByStatusAndAccountId(SystemConstant.STRING_ACTIVE_STATUS, accountDTO.getId());
        boolean isOrderExist = orderService.isOrderExistByProductIdAndCartIdAndStatus(productId, cartDTO.getId(), SystemConstant.STRING_ACTIVE_STATUS);
        
        if (!isOrderExist) {
            OrderDTO orderDTO = new OrderDTO();
            ProductDTO productDTO = productService.findOneById(productId);
            orderDTO.setProduct(productDTO);
            orderDTO.setCart(cartDTO);
            
            orderDTO.setStatus(SystemConstant.STRING_ACTIVE_STATUS);
            orderDTO.setQuantity(quantity);

            orderService.save(orderDTO);

        } else {
            OrderDTO existedOrder = orderService.findOneByProductIdAndCartIdAndStatus(productId, cartDTO.getId(), SystemConstant.STRING_ACTIVE_STATUS);
            if (quantity + existedOrder.getQuantity() > existedOrder.getProduct().getStock()) {
                existedOrder.setQuantity(existedOrder.getProduct().getStock());
            } else {    
                existedOrder.setQuantity(quantity + existedOrder.getQuantity());
            }
            orderService.save(existedOrder);
        }
            
        return new RedirectView("/product/detail/" + productId);
    }
    
    @DeleteMapping
    public void delete(@RequestBody String id) {
        orderService.delete(id.substring(1, id.length() - 1));
    }
}