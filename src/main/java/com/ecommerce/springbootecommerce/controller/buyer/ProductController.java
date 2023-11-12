package com.ecommerce.springbootecommerce.controller.buyer;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.springbootecommerce.constant.RedisConstant;
import com.ecommerce.springbootecommerce.constant.StatusConstant;
import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.constant.enums.order.OrderStatus;
import com.ecommerce.springbootecommerce.dto.BaseDTO;
import com.ecommerce.springbootecommerce.dto.CartDTO;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.dto.OrderItemDTO;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.service.ICartService;
import com.ecommerce.springbootecommerce.service.IOrderItemService;
import com.ecommerce.springbootecommerce.service.IOrderService;
import com.ecommerce.springbootecommerce.service.IProductService;
import com.ecommerce.springbootecommerce.util.RedisUtil;

@Controller(value = "ProductControllerOfBuyer")
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;
    
    @Autowired
    private ICartService cartService;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private IOrderItemService orderItemService;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @GetMapping("/detail/{id}")
    public String displayProduct(
        @PathVariable("id") long id,
        @RequestParam(value = "message", required = false) String message,
        Model model, RedirectAttributes redirectAttributes
    ) {
        ProductDTO dto = productService.findById(id);
        
        // Handle if product is not available
        if (!dto.getStatus().equals(StatusConstant.STRING_ACTIVE_STATUS)) {
            redirectAttributes.addFlashAttribute("message", "The product does not exist");
            return "redirect:/error";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!auth.getPrincipal().equals(SystemConstant.ANONYMOUS_USER)) {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
    
            CartDTO cartDTO = cartService.findOneByAccountId(userDetails.getId());
            OrderDTO orderDTO = orderService.findOneByCartIdAndAccountIdAndStatus(cartDTO.getId(), dto.getAccount().getId(), OrderStatus.PENDING);
            
            long[] quantityAddedProducts = new long[dto.getProductItems().size()];
            if(orderDTO == null) {
                Arrays.fill(quantityAddedProducts, 0);
            } else {
                for(int i = 0; i < dto.getProductItems().size(); i++) {
                    OrderItemDTO orderItemDTO = orderItemService.findOneByOrderIdAndProductItemId(orderDTO.getId(), dto.getProductItems().get(i).getId());
                    if(null != orderItemDTO) {
                        quantityAddedProducts[i] = orderItemDTO.getQuantity();
                    }
                }
            }
    
            if(message != null) {
                model.addAttribute("message", message);
            }

            model.addAttribute("quantityAddedProducts", quantityAddedProducts);
            model.addAttribute(RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER, redisUtil.getHashField(RedisConstant.REDIS_USER_INFO + userDetails.getUsername(), RedisConstant.REDIS_USER_INFO_QUANTITY_ORDER));
        }

        BaseDTO<ProductDTO> suggestProducts = productService.findAllByAccountIdAndStatus(dto.getAccount().getId(), StatusConstant.STRING_ACTIVE_STATUS, 1, 10);
        
        model.addAttribute("product", dto);
        model.addAttribute("suggestProducts", suggestProducts);
        model.addAttribute("isVariation", dto.getProductItems().get(0).getVariationName() != null); 
        return "/buyer/detailProduct";
    }
}
