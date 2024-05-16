package com.ecommerce.springbootecommerce.api.manager;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.CategoryDTO;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.exception.CustomException;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.service.ICategoryService;

@RestController(value = "categoryAPIOfManager")
@RequestMapping(value = "/api/v1/manager/category")
public class CategoryAPI {

    private final ICategoryService categoryService;

    private final IAccountService accountService;

    public CategoryAPI(ICategoryService categoryService, IAccountService accountService) {
        this.categoryService = categoryService;
        this.accountService = accountService;
    }

    @PostMapping()
    public ResponseEntity<String> addCategory(
            @RequestBody CategoryDTO category) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        try {
            AccountDTO accountDTO = accountService.findById(userDetails.getId());
            category.setAccount(accountDTO);
            categoryService.save(category);
            return ResponseEntity.ok("Success! Your category has been created.");
        } catch (Exception e) {
            CustomException customException = (CustomException) e.fillInStackTrace();
            return ResponseEntity.status(customException.getErrorCode()).body(e.getMessage());
        }
    }

    @PutMapping()
    public ResponseEntity<String> updateCategory(
            @RequestBody CategoryDTO dto) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        try {
            CategoryDTO prevCategory = categoryService.findOneByIdAndAccountId(dto.getId(), userDetails.getId());
            if (prevCategory == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category does not exist");
            }

            AccountDTO accountDTO = accountService.findById(userDetails.getId());
            dto.setAccount(accountDTO);
            categoryService.save(dto);
            return ResponseEntity.ok("Success! Your category has been updated.");
        } catch (Exception e) {
            CustomException customException = (CustomException) e.fillInStackTrace();
            return ResponseEntity.status(customException.getErrorCode()).body(e.getMessage());
        }
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteCategory(
            @RequestBody CategoryDTO dto) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        try {
            CategoryDTO prevCategory = categoryService.findOneByIdAndAccountId(dto.getId(), userDetails.getId());
            if (prevCategory == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category does not exist");
            }

            categoryService.delete(dto.getId());
            return ResponseEntity.ok("Success! Your category has been deleted.");
        } catch (Exception e) {
            CustomException customException = (CustomException) e.fillInStackTrace();
            return ResponseEntity.status(customException.getErrorCode()).body(e.getMessage());
        }
    }

}
