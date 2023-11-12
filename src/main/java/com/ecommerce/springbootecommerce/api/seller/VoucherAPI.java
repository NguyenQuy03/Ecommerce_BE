package com.ecommerce.springbootecommerce.api.seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.dto.VoucherDTO;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.service.IVoucherService;

@RestController(value = "VoucherAPIOfSeller")
@RequestMapping("/api/seller/voucher")
public class VoucherAPI {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IVoucherService voucherService;
    
    @PostMapping
    public ResponseEntity<String> save(
        @RequestBody VoucherDTO dto
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        boolean isVoucherExist = voucherService.isExistByCodeAndAccountId(dto.getCode(), userDetails.getId());
        
        if (isVoucherExist) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Voucher Code has already exist");
        }

        try {
            AccountDTO account = accountService.findById(userDetails.getId());
            dto.setAccount(account);

            for(GrantedAuthority grAuth : userDetails.getAuthorities()) {
                if(grAuth.getAuthority().equals("ROLE_MANAGER")) {
                    dto.getAccount().setMainRole(SystemConstant.ROLE_MANAGER);
                    break;
                }
                
                if(grAuth.getAuthority().equals("ROLE_SELLER")) {
                    dto.getAccount().setMainRole(SystemConstant.ROLE_SELLER);
                    break;
                }
            }

            if(dto.getAccount().getMainRole() == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission");
            }

            voucherService.save(dto);
            
            return ResponseEntity.ok("Success! Your voucher has been published.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error publishing voucher");
        }
    }

    @PutMapping
    public ResponseEntity<String> update(
        @RequestBody VoucherDTO dto
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        try {
            AccountDTO account = accountService.findById(userDetails.getId());
            dto.setAccount(account);

            voucherService.update(dto);
            
            return ResponseEntity.ok("Success! Your voucher has been updated.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating voucher");
        }
    }
}
