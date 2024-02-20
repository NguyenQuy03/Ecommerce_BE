package com.ecommerce.springbootecommerce.api.buyer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.constant.enums.product.ProductStatus;
import com.ecommerce.springbootecommerce.dto.BaseDTO;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.service.IProductService;

@RestController
@RequestMapping("/api/v1/buyer/product")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductAPI {

    @Autowired
    private IProductService productService;

    @GetMapping
    public ResponseEntity<?> getProducts() {
        try {
            Pageable pageable = PageRequest.of(0, 12);
            List<ProductDTO> dto = productService.findAllByStatus(ProductStatus.ACTIVE, pageable);
            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error get products");
        }
    }

    @GetMapping("/recommend/seller")
    public ResponseEntity<?> getProductsBySeller(
        @RequestParam("sellerId") long sellerId
    ) {
        try {
            BaseDTO<ProductDTO> dto = productService.findAllByAccountIdAndStatus(sellerId, ProductStatus.ACTIVE, 1, 12);
            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error get products by seller_id");
        }
    }

    @GetMapping("/recommend/category")
    public List<ProductDTO> getProductsByCategory() {

        Pageable pageable = PageRequest.of(0, 12);
        List<ProductDTO> productItems = productService.findAllByStatus(ProductStatus.ACTIVE, pageable);

        return productItems;
    }

    @GetMapping("/detail")
    public ResponseEntity<ProductDTO> displayProduct(
            @RequestParam("id") long id
    ) {
        ProductDTO dto = productService.findById(id);

        return ResponseEntity.ok().body(dto);
    }
}
