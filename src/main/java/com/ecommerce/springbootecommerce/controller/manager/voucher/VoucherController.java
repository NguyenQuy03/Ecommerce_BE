package com.ecommerce.springbootecommerce.controller.manager.voucher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.dto.VoucherDTO;
import com.ecommerce.springbootecommerce.service.IVoucherService;

@Controller(value = "VoucherControllerOfManager")
@RequestMapping("/manager/voucher")
public class VoucherController {

    @Autowired
    private IVoucherService voucherService;

    @GetMapping
    public String displayForm(
        Model model
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername(userDetails.getUsername());

        
        VoucherDTO dto = new VoucherDTO();
        dto.setAccount(accountDTO);

        model.addAttribute("dto", dto);
        model.addAttribute("categories", "categories");
        return "manager/voucher/voucher";
    }

    @GetMapping("/{id}")
    public String displayEditForm(
            @PathVariable("id") long id,
            Model model
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        VoucherDTO dto = voucherService.findOneByIdAndAccountId(id, userDetails.getId());
        
        if (dto == null) {
            model.addAttribute("message", SystemConstant.ACCESS_EXCEPTION);
            return "error/error";
        }

        model.addAttribute("dto", dto);

        return "/manager/voucher/voucher";
    }
}
