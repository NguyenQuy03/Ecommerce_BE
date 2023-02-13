package com.ecommerce.springbootecommerce.controller.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
        PageRequest pageRequest = PageRequest.of(0, 20);
        List<OrderDTO> dtos = orderService.findAllByStatus(SystemConstant.STRING_DELIVERED_ORDER, pageRequest);
        
        OrderDTO dto = new OrderDTO();
        dto.setListResult(dtos);
        model.addAttribute("dto", dto);
        return "manager/transactions";
    }
}
