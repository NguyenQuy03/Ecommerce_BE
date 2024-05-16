package com.ecommerce.springbootecommerce.api.buyer;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.constant.enums.product.ProductStatus;
import com.ecommerce.springbootecommerce.dto.BaseDTO;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.exception.CustomException;
import com.ecommerce.springbootecommerce.service.IProductService;
import com.ecommerce.springbootecommerce.service.impl.ProductService;

@RestController
@RequestMapping("/api/v1/buyer/product")
public class ProductAPI {

    private final IProductService productService;

    public ProductAPI(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Object> getProducts() {
        try {
            Pageable pageable = PageRequest.of(0, 12);
            List<ProductDTO> dto = productService.findAllWithoutStatus(ProductStatus.REMOVED, pageable);
            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            return ResponseEntity.status(((CustomException) e.fillInStackTrace()).getErrorCode()).body(e.getMessage());
        }
    }

    @GetMapping("/recommend/seller")
    public ResponseEntity<Object> getProductsBySeller(
            @RequestParam("sellerId") long sellerId) {
        try {
            BaseDTO<ProductDTO> dto = productService.findAllByAccountIdAndStatus(sellerId, ProductStatus.LIVE, 1, 12);
            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            return ResponseEntity.status(((CustomException) e.fillInStackTrace()).getErrorCode()).body(e.getMessage());
        }
    }

    @GetMapping("/recommend/category")
    public List<ProductDTO> getProductsByCategory() {

        Pageable pageable = PageRequest.of(0, 12);

        return productService.findAllByStatus(ProductStatus.LIVE, pageable);
    }

    @GetMapping("/detail")
    public ResponseEntity<ProductDTO> displayProduct(
            @RequestParam("id") long id) {
        ProductDTO dto = productService.findById(id);

        return ResponseEntity.ok().body(dto);
    }
}
