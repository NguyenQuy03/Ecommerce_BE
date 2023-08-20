package com.ecommerce.springbootecommerce.api.seller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.product.ProductDTO;
import com.ecommerce.springbootecommerce.service.IProductService;

@RestController
@RequestMapping("/api/seller/product")
public class ProductAPI {
    
    @Autowired  
    private IProductService productService;

    @PostMapping()
    public void save(
            @RequestBody ProductDTO product
    ) throws IOException {
        product.setId(null);
        product.setStatus(SystemConstant.STRING_ACTIVE_STATUS);
        
        productService.save(product);
    }
    
    @PutMapping()
    public void update(
            @RequestBody ProductDTO product  
    ) throws IOException {
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
