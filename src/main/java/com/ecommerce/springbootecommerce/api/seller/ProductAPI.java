package com.ecommerce.springbootecommerce.api.seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.service.IProductService;


@RestController()
@RequestMapping("/api/seller")
public class ProductAPI {
    
    @Autowired
    private IProductService productService;
    
    @PostMapping(value="/product")
    public ProductDTO createProduct(@RequestBody ProductDTO model) {
        return productService.save(model);
    }
    
    @PutMapping(value="/product/{id}")
    public ProductDTO updateProduct(@RequestBody ProductDTO model, @PathVariable("id") Long id) {
        model.setId(id);
        return productService.save(model);
    }
    
    @DeleteMapping(value="/product")
    public void deleteProduct(@RequestBody long[] ids) {
        
    }
    
}
