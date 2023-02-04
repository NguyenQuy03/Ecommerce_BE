package com.ecommerce.springbootecommerce.controller.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.AccountRoleDTO;
import com.ecommerce.springbootecommerce.service.IAccountRoleService;
import com.ecommerce.springbootecommerce.service.IAccountService;

@Controller
@RequestMapping("/manager")
public class AccountIFController {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IAccountRoleService accountRoleService;

    @GetMapping(value = "/account/sellerAccount")
    public String sellerAccount(
            Model model) {

        List<AccountDTO> listAccount = getListAccount(SystemConstant.ROLE_SELLER);

        AccountDTO dto = new AccountDTO();
        dto.setListResult(listAccount);
        model.addAttribute("dto", dto);

        return "manager/accountInfo";
    }

    @GetMapping(value = "/account/buyerAccount")
    public String buyerAccount(Model model) {
        List<AccountDTO> listAccount = getListAccount(SystemConstant.ROLE_BUYER);

        AccountDTO dto = new AccountDTO();
        dto.setListResult(listAccount);
        model.addAttribute("dto", dto);
        return "manager/accountInfo";
    }

    private List<AccountDTO> getListAccount(String roleCode) {
        Set<AccountRoleDTO> accountRoleDTOs = accountRoleService.findAllByRole(roleCode);
        List<AccountDTO> listAccount = new ArrayList<>();
        for (AccountRoleDTO dto : accountRoleDTOs) {
            AccountDTO accountDTO = accountService.findOneById(dto.getAccountId());
            listAccount.add(accountDTO);
        }

        return listAccount;
    }

}
