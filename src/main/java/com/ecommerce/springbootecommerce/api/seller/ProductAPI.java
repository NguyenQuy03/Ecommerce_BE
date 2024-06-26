package com.ecommerce.springbootecommerce.api.seller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.constant.enums.product.ProductStatus;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.service.IProductService;

@RestController(value = "ProductAPIOfSeller")
@RequestMapping("/api/v1/seller/product")
public class ProductAPI {

    private final IProductService productService;

    private final IAccountService accountService;

    public ProductAPI(IProductService productService, IAccountService accountService) {
        this.productService = productService;
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<String> save(
            @RequestBody ProductDTO dto) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        AccountDTO account = accountService.findById(userDetails.getId());
        dto.setAccount(account);
        try {
            productService.save(dto);
            return ResponseEntity.ok("Success! Your product has been published.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error publishing product");
        }
    }

    @PutMapping()
    public ResponseEntity<String> update(
            @RequestBody ProductDTO dto) {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();

            AccountDTO account = accountService.findById(userDetails.getId());
            dto.setAccount(account);
            productService.update(dto);
            return ResponseEntity.ok("Success! Your product has been updated.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating product");
        }
    }

    @DeleteMapping()
    public ResponseEntity<String> softDelete(@RequestBody long[] ids) {
        try {
            productService.softDelete(ProductStatus.INACTIVE, ids);
            return ResponseEntity.ok("Success! Your product has been deleted.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting product");
        }
    }

    @DeleteMapping("/forceDelete")
    public ResponseEntity<String> forceDelete(@RequestBody long[] ids) {
        try {
            productService.forceDelete(ProductStatus.REMOVED, ids);
            return ResponseEntity.ok("Success! Your product has been deleted.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting product");
        }
    }

    @PostMapping("/restore")
    public ResponseEntity<String> restore(@RequestBody Long id) {
        try {
            productService.restore(id);
            return ResponseEntity.ok("Success! Your product has been force deleted.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error force deleting product");
        }
    }
}
