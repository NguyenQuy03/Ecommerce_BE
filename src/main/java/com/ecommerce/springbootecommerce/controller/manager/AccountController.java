package com.ecommerce.springbootecommerce.controller.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.AccountRoleDTO;
import com.ecommerce.springbootecommerce.service.IAccountRoleService;

@Controller(value = "AccountControllerOfManager")
@RequestMapping("/manager")
public class AccountController {

    @Autowired
    private IAccountRoleService accountRoleService;

    @GetMapping(value = "/account/sellerAccount")
    public String sellerAccount(
            Model model
    ){
        List<AccountRoleDTO> accounts = accountRoleService.findAllByRole(SystemConstant.ROLE_SELLER);
        AccountRoleDTO dto = new AccountRoleDTO();
        dto.setListResult(accounts);

        model.addAttribute("dto", dto);
        
        return "manager/accountInfo";
    }
    
    @GetMapping(value = "/account/buyerAccount")
    public String buyerAccount(Model model) {
        List<AccountRoleDTO> accounts = accountRoleService.findAllByRole(SystemConstant.ROLE_BUYER);
        AccountRoleDTO dto = new AccountRoleDTO();
        dto.setListResult(accounts);

        model.addAttribute("dto", dto);
        return "manager/accountInfo";
    }
}
