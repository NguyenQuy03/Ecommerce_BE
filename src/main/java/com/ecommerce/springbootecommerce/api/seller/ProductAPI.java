package com.ecommerce.springbootecommerce.api.seller;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api/seller/product")
public class ProductAPI {
    
    @Autowired  
    private IProductService productService;

    @PostMapping()
    public void save(
            @RequestBody ProductDTO product
    ) throws IOException {

        product.setSold(0L);
        product.setId(null);
        product.setStatus(SystemConstant.STRING_ACTIVE_STATUS);

        productService.save(product);
    }
    
    @PutMapping()
    public void update(
            @RequestBody ProductDTO product
    ) throws IOException {
        
        String author = SecurityContextHolder.getContext().getAuthentication().getName();
        product.setModifiedBy(author);
        product.setModifiedDate(new Date());
        productService.update(product);
    }

    @DeleteMapping()
    public void softDelete(@RequestBody String[] ids) {
        productService.softDelete(SystemConstant.INACTIVE_PRODUCT, ids);
    }

    @DeleteMapping("/forceDelete")
    public void forceDelete(@RequestBody String[] ids) {
        productService.forceDelete(SystemConstant.REMOVED_PRODUCT, ids);
    }
    
    @PostMapping("/restore")
    public void restore(@RequestBody String id) {
        productService.restore(id.substring(1, id.length() - 1));
    }
}
