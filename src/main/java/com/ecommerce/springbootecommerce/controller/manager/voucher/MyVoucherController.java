package com.ecommerce.springbootecommerce.controller.manager.voucher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.constant.enums.voucher.VoucherStatus;
import com.ecommerce.springbootecommerce.dto.BaseDTO;
import com.ecommerce.springbootecommerce.dto.CustomUserDetails;
import com.ecommerce.springbootecommerce.dto.VoucherDTO;
import com.ecommerce.springbootecommerce.service.IVoucherService;

@Controller(value = "MyVoucherControllerOfManager")
@RequestMapping("/manager/voucher/list")
public class MyVoucherController {

    @Autowired
    private IVoucherService voucherService;
    
    @GetMapping("/all")
    public String displayAll(
        Model model,
        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
        @RequestParam(value = "size", required = false, defaultValue = SystemConstant.DEFAULT_PAGE_SIZE) int size
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        BaseDTO<VoucherDTO> dto = voucherService.findAllByAccountId(userDetails.getId(), page - 1, size);

        return generic(model, dto, "all");
    }
    
    @GetMapping("/ongoing")
    public String displayOnGoing(
        Model model,
        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
        @RequestParam(value = "size", required = false, defaultValue = SystemConstant.DEFAULT_PAGE_SIZE) int size
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        BaseDTO<VoucherDTO> dto = voucherService.findAllByAccountIdAndStatus(userDetails.getId(), VoucherStatus.ONGOING, page - 1, size);

        return generic(model, dto, "ongoing");
    }
    
    @GetMapping("/upcoming")
    public String displayUpComing(
        Model model,
        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
        @RequestParam(value = "size", required = false, defaultValue = SystemConstant.DEFAULT_PAGE_SIZE) int size
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        BaseDTO<VoucherDTO> dto = voucherService.findAllByAccountIdAndStatus(userDetails.getId(), VoucherStatus.UPCOMING, page - 1, size);

        return generic(model, dto, "upcoming");
    }
    
    @GetMapping("/expired")
    public String displayExpired(
        Model model,
        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
        @RequestParam(value = "size", required = false, defaultValue = SystemConstant.DEFAULT_PAGE_SIZE) int size
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        BaseDTO<VoucherDTO> dto = voucherService.findAllByAccountIdAndStatus(userDetails.getId(), VoucherStatus.EXPIRED, page - 1, size);

        return generic(model, dto, "expired");
    }

    
    private String generic(Model model, BaseDTO<VoucherDTO> dto, String tab) {
        
        model.addAttribute("tab", tab);
        model.addAttribute(SystemConstant.QUANTITY_VOUCHER_DTO, dto == null ? 0 : dto.getTotalItem());
        model.addAttribute("dto", dto);
        return "manager/voucher/myVoucher";
    }
}
