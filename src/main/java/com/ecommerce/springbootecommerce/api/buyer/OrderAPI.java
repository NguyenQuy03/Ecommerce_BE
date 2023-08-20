package com.ecommerce.springbootecommerce.api.buyer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.service.ICartService;
import com.ecommerce.springbootecommerce.service.IOrderService;
import com.ecommerce.springbootecommerce.service.IProductService;

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
//        CartDTO cartDTO = cartService.findOneByAccountId(accountDTO.getId());
//        boolean isOrderExist = orderService.isOrderExistByProductIdAndCartIdAndStatus(productId, cartDTO.getId(), SystemConstant.STRING_ACTIVE_STATUS);
//
//        if (!isOrderExist) {
//            OrderDTO orderDTO = new OrderDTO();
//            ProductDTO productDTO = productService.findOneById(productId);
////            orderDTO.setProduct(productDTO);
////            orderDTO.setCart(cartDTO);
//
//            orderDTO.setStatus(SystemConstant.STRING_ACTIVE_STATUS);
//            //.setQuantity(quantity);
//
//            orderService.save(orderDTO);
//
//        } else {
//            OrderDTO existedOrder = orderService.findOneByProductIdAndCartIdAndStatus(productId, cartDTO.getId(), SystemConstant.STRING_ACTIVE_STATUS);
////            if (quantity + existedOrder.getQuantity() > existedOrder.getProduct().getStock()) {
////                existedOrder.setQuantity(existedOrder.getProduct().getStock());
////            } else {
////                existedOrder.setQuantity(quantity + existedOrder.getQuantity());
////            }
//            orderService.save(existedOrder);
//        }
            
        return new RedirectView("/product/detail/" + productId);
    }
    
    @DeleteMapping
    public void delete(@RequestBody String id) {
        orderService.delete(id.substring(1, id.length() - 1));
    }
}