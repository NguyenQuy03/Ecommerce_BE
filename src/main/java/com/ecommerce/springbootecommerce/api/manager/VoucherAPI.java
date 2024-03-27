package com.ecommerce.springbootecommerce.api.manager;

import java.util.List;

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
import com.ecommerce.springbootecommerce.dto.VoucherDTO;
import com.ecommerce.springbootecommerce.exception.CustomException;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.service.ICategoryService;
import com.ecommerce.springbootecommerce.service.IVoucherService;

@RestController(value = "VoucherAPIOfManager")
@RequestMapping("/api/v1/manager/voucher")
public class VoucherAPI {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IVoucherService voucherService;

    @Autowired
    private ICategoryService categoryService;

    @PostMapping
    public ResponseEntity<String> save(
            @RequestBody VoucherDTO dto) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        boolean isVoucherExist = voucherService.isExistByCodeAndAccountId(dto.getCode(), userDetails.getId());

        if (isVoucherExist) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Voucher Code has already exist");
        }

        try {
            AccountDTO account = accountService.findById(userDetails.getId());
            List<CategoryDTO> categories = categoryService.findAllByAccountId(userDetails.getId());

            dto.setAccount(account);
            dto.setCategories(categories);

            voucherService.save(dto);

            return ResponseEntity.ok("Success! Your voucher has been published.");
        } catch (Exception e) {
            return ResponseEntity.status(((CustomException) e.fillInStackTrace()).getErrorCode()).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> update(
            @RequestBody VoucherDTO dto) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        try {
            AccountDTO account = accountService.findById(userDetails.getId());
            dto.setAccount(account);

            voucherService.update(dto);

            return ResponseEntity.ok("Success! Your voucher has been updated.");
        } catch (Exception e) {
            return ResponseEntity.status(((CustomException) e.fillInStackTrace()).getErrorCode()).body(e.getMessage());
        }
    }
}
