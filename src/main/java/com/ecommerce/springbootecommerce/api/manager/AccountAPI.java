package com.ecommerce.springbootecommerce.api.manager;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.constant.enums.account.AccountRole;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.AccountRoleDTO;
import com.ecommerce.springbootecommerce.exception.CustomException;
import com.ecommerce.springbootecommerce.service.IAccountRoleService;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.service.impl.AccountRoleService;
import com.ecommerce.springbootecommerce.service.impl.AccountService;

@RestController(value = "AccountAPIOfManager")
@RequestMapping("/api/v1/manager/account")
public class AccountAPI {

    private final IAccountRoleService accountRoleService;

    private final IAccountService accountService;

    public AccountAPI(AccountRoleService accountRoleService, AccountService accountService) {
        this.accountRoleService = accountRoleService;
        this.accountService = accountService;
    }

    @GetMapping()
    public ResponseEntity<Object> getAccounts(
            @RequestParam Map<String, String> params) {
        try {
            String type = params.get("type");
            List<AccountDTO> accounts = accountRoleService.findAllByRoleCodeIn(AccountRole.getRoles(type))
                    .stream()
                    .map(AccountRoleDTO::getAccount)
                    .collect(Collectors.toList());

            return ResponseEntity.ok().body(accounts);
        } catch (Exception e) {
            return ResponseEntity.status(((CustomException) e.fillInStackTrace()).getErrorCode()).body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> changeAccountStatus(@RequestBody long[] ids, @RequestParam String action) {
        try {
            accountService.changeAccountStatus(ids, action);
            return ResponseEntity.ok().body("Change status successfully");
        } catch (Exception e) {
            return ResponseEntity.status(((CustomException) e.fillInStackTrace()).getErrorCode()).body(e.getMessage());
        }
    }
}
