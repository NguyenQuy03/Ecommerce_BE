package com.ecommerce.springbootecommerce.controller.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.OrderDTO;
import com.ecommerce.springbootecommerce.service.IOrderService;


@Controller
@RequestMapping("/manager")
public class TransactionController {
    
    @Autowired
    private IOrderService orderService;

    @GetMapping(value="/transaction")
    public String transactions(Model model) {
        List<OrderDTO> dtos = orderService.findAllByStatus(SystemConstant.DELIVERED_STATUS);
        
        OrderDTO dto = new OrderDTO();
        dto.setListResult(dtos);
        model.addAttribute("dto", dto);
        return "manager/transaction";
    }
}
