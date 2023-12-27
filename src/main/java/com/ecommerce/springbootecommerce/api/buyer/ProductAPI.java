package com.ecommerce.springbootecommerce.api.buyer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.constant.enums.product.ProductStatus;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.service.IProductService;

@RestController
@RequestMapping("/api/v1/buyer/product")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductAPI {

    @Autowired
    private IProductService productService;

    @GetMapping
    public List<ProductDTO> getProducts() {

        Pageable pageable = PageRequest.of(0, 12);
        List<ProductDTO> productItems = productService.findAllByStatus(ProductStatus.ACTIVE, pageable);

        return productItems;
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ProductDTO> displayProduct(
            @PathVariable("id") long id
    ) {
        ProductDTO dto = productService.findById(id);

        return ResponseEntity.ok().body(dto);
    }
}
