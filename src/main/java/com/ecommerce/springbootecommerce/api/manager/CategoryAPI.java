package com.ecommerce.springbootecommerce.api.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.CategoryDTO;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.service.ICategoryService;

@RestController(value = "CategoryAPIOfManager")
@RequestMapping(value = "/api/v1/manager/category")
public class CategoryAPI {
    
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IAccountService accountService;
    
    @PostMapping()
    public ResponseEntity<String> addCategory(
            @RequestBody CategoryDTO category 
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        try {
            AccountDTO accountDTO = accountService.findById(userDetails.getId());
            category.setAccount(accountDTO);
            categoryService.save(category);
            return ResponseEntity.ok("Success! Your category has been created.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error create category");
        }
    }

    @PutMapping()
    public ResponseEntity<String> editCategory(
            @RequestBody CategoryDTO category 
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        try {
            AccountDTO accountDTO = accountService.findById(userDetails.getId());
            category.setAccount(accountDTO);
            categoryService.save(category);
            return ResponseEntity.ok("Success! Your category has been updated.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error update category");
        }
    }

}
