package com.ecommerce.springbootecommerce.api.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.constant.enums.product.ProductStatus;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.exception.CustomException;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.service.IProductService;

@RestController(value = "ProductAPIOfManager")
@RequestMapping(value = "/api/v1/manager/product")
public class ProductAPI {

    @Autowired
    private IProductService productService;

    @Autowired
    private IAccountService accountService;

    @GetMapping()
    public ResponseEntity<?> getProducts(
            @RequestParam(value = "type", required = false, defaultValue = "all") String type,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = SystemConstant.DEFAULT_PAGE_SIZE) int size) {
        try {
            if (ProductStatus.get(type) == null) {
                return ResponseEntity.badRequest().body("Bad Request");
            }

            List<ProductDTO> dto = null;

            if (ProductStatus.get(type).equals(ProductStatus.ALL)) {
                dto = productService.findAllWithoutStatus(ProductStatus.REMOVED,
                        PageRequest.of(page - 1, size));
            } else {
                dto = productService.findAllByStatus(ProductStatus.get(type),
                        PageRequest.of(page - 1, size));
            }

            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(value = "edit")
    public ResponseEntity<?> editProduct(
            @RequestParam(value = "id", required = true) Long productId) {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            ProductDTO dto = productService.findOneByIdAndAccountId(productId, userDetails.getId());

            if (dto.equals(null)) {
                ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE);
            }

            if (dto.getStatus().equals(ProductStatus.REMOVED)) {
                ResponseEntity.status(HttpStatus.NO_CONTENT);
            }

            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            return ResponseEntity.status(((CustomException) e.fillInStackTrace()).getErrorCode()).body(e.getMessage());
        }
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

}
