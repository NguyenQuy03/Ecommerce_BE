package com.ecommerce.springbootecommerce.controller.seller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.springbootecommerce.dto.CategoryDTO;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.service.ICategoryService;
import com.ecommerce.springbootecommerce.service.IProductService;

@Controller
@RequestMapping("seller/product")
public class ProductController {
    
    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @GetMapping("list/all")
    public String allProduct(
            Model model,
            @RequestParam("page") int page,
            @RequestParam("size") int size
        ) {
            long quantityProduct = productService.countTotalProduct(); 
            
            Pageable pageable = PageRequest.of(page - 1, size);
            List<ProductDTO> listProduct = productService.findAll(pageable);
            Integer totalPage = (int) Math.ceil((double) quantityProduct / size);
            
            ProductDTO dto = new ProductDTO();
            dto.setTotalPage(totalPage);
            dto.setListResult(listProduct);
            dto.setPage(page);
            dto.setSize(size);

            model.addAttribute("quantityProduct", quantityProduct);
            model.addAttribute("dto", dto);
            
            return "seller/product/myProduct/allProduct";
        }

    @GetMapping("list/live")
    public String liveProduct(
            Model model,
            @RequestParam("page") int page,
            @RequestParam("size") int size
        ) {
        
        long quantityLiveProduct = productService.countLiveProduct();

        Pageable pageable = PageRequest.of(page - 1, size);
        List<ProductDTO> liveProducts = productService.findLiveProduct(pageable);
        Integer totalPage = (int) Math.ceil((double) quantityLiveProduct / size);
        
        ProductDTO dto = new ProductDTO();
        dto.setTotalPage(totalPage);
        dto.setListResult(liveProducts);
        dto.setPage(page);
        dto.setSize(size);

        model.addAttribute("quantityLiveProduct", quantityLiveProduct);
        model.addAttribute("dto", dto);

        return "seller/product/myProduct/liveProduct";
    }
    
    @GetMapping("list/soldout")
    public String soldOutProduct(
            Model model,
            @RequestParam("page") int page,
            @RequestParam("size") int size
        ) {
        
        long quantitySoldOutProduct = productService.countSoldOutProduct();

        Pageable pageable = PageRequest.of(page - 1, size);
        List<ProductDTO> soldOutProducts = productService.findSoldOutProduct(pageable);
        Integer totalPage = (int) Math.ceil((double) quantitySoldOutProduct / size);
        
        ProductDTO dto = new ProductDTO();
        dto.setTotalPage(totalPage);
        dto.setListResult(soldOutProducts);
        dto.setPage(page);
        dto.setSize(size);

        model.addAttribute("quantitySoldOutProduct", quantitySoldOutProduct);
        model.addAttribute("dto", dto);

        return "seller/product/myProduct/soldOutProduct";
    }

//    @GetMapping("list/reviewing")
//    public String reviewingProduct(Model model) {
//        List<ProductDTO> liveProducts = productService.findLiveProduct();
//
//        model.addAttribute("liveProducts", liveProducts);
//
//        return "seller/product/myProduct/reviewingProduct";
//    }

    @GetMapping("new")
    public String newProduct(Model model) {
        model.addAttribute("product", new ProductDTO());
        List<CategoryDTO> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        return "seller/product/newProduct";
    }
}
