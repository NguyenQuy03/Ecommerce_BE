package com.ecommerce.springbootecommerce.controller.buyer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.service.IProductService;
import com.ecommerce.springbootecommerce.util.QuantityOrderUtil;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private IProductService productService;

    @Autowired
    private QuantityOrderUtil quantityOrderUtil;

    @GetMapping("/category/{id}")
    public String categoryPage(
            Model model,
            @PathVariable("id") long id,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {

        Pageable pageable = PageRequest.of(page - 1, size);

        long quantityProduct = productService.countAllByCategoryId(id);

        Integer totalPage = (int) Math.ceil((double) quantityProduct / size);
        List<ProductDTO> products = productService.findAllByCategoryId(id, pageable);

        ProductDTO dto = new ProductDTO();
        dto.setTotalPage(totalPage);
        dto.setListResult(products);
        dto.setPage(page);
        dto.setSize(size);

        model.addAttribute("quantityOrder", quantityOrderUtil.getQuantityOrder());
        model.addAttribute("dto", dto);

        return "buyer/search";
    }

    @SuppressWarnings("null")
    @GetMapping()
    public String searchPage(
            Model model,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size
    ) {
        if (page == null && size == null) {
            page = 1;
            size = 9;
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        long quantityProduct = 0L;
        List<ProductDTO> products = new ArrayList<>();
        if (keyword == "") {
            quantityProduct = productService.countAllByStatus(SystemConstant.STRING_ACTIVE_STATUS);
            products = productService.findAllByStatus(SystemConstant.STRING_ACTIVE_STATUS, pageable);
        } else {
            quantityProduct = productService.countByNameContains(keyword);
            products = productService.findAllByNameContains(keyword, pageable);
        }
        

        Integer totalPage = (int) Math.ceil((double) quantityProduct / size);

        ProductDTO dto = new ProductDTO();
        dto.setTotalPage(totalPage);
        dto.setListResult(products);
        dto.setPage(page);
        dto.setSize(size);

        model.addAttribute("keyword",  keyword);
        model.addAttribute("quantityOrder", quantityOrderUtil.getQuantityOrder());
        model.addAttribute("dto", dto);
        
        return "buyer/search";
    }
}
