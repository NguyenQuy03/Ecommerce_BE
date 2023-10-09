package com.ecommerce.springbootecommerce.controller.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.RoleDTO;
import com.ecommerce.springbootecommerce.service.IRoleService;

@Controller(value = "AccountControllerOfManager")
@RequestMapping("/manager")
public class AccountController {

    @Autowired
    private IRoleService roleService;

    @GetMapping(value = "/account/sellerAccount")
    public String sellerAccount(
            Model model
    ){
        ArrayList<AccountDTO> listAccount = (ArrayList<AccountDTO>) getListAccount(SystemConstant.ROLE_SELLER);
        AccountDTO dto = new AccountDTO();
        dto.setListResult(listAccount);
        model.addAttribute("dto", dto);
        
        return "manager/accountInfo";
    }
    
    @GetMapping(value = "/account/buyerAccount")
    public String buyerAccount(Model model) {
        ArrayList<AccountDTO> listAccount = (ArrayList<AccountDTO>) getListAccount(SystemConstant.ROLE_BUYER);
        
        AccountDTO dto = new AccountDTO();
        dto.setListResult(listAccount);
        model.addAttribute("dto", dto);
        return "manager/accountInfo";
    }

    private List<AccountDTO> getListAccount(String roleCode) {
        RoleDTO roleDTO = roleService.findOneByCode(roleCode);
        return roleDTO.getAccounts();
    }

}
