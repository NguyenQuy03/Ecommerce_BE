package com.ecommerce.springbootecommerce.controller.seller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.springbootecommerce.api.seller.ProductAPI;
import com.ecommerce.springbootecommerce.dto.AccountDTO;
import com.ecommerce.springbootecommerce.dto.CategoryDTO;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.service.IAccountService;
import com.ecommerce.springbootecommerce.service.ICategoryService;
import com.ecommerce.springbootecommerce.service.IProductService;

@Controller
@RequestMapping("seller/product")
public class ProductController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private ProductAPI productAPI;

    @GetMapping("list/all")
    public String allProduct(
            Model model,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDTO account = accountService.findAccountByUserName(userName);

        long quantityProduct = productService.countAllByAccountId(account.getId());

        Pageable pageable = PageRequest.of(page - 1, size);
        List<ProductDTO> listProduct = productService.findAllByAccountId(account.getId(), pageable);
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
            @RequestParam("size") int size) {

        long quantityLiveProduct = productService.countByStockGreaterThan(0);

        Pageable pageable = PageRequest.of(page - 1, size);
        List<ProductDTO> liveProducts = productService.findByStockGreaterThan(0, pageable);
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
    ){
        long quantitySoldOutProduct = productService.countByStockEquals(0);

        Pageable pageable = PageRequest.of(page - 1, size);
        List<ProductDTO> soldOutProducts = productService.findByStockEquals(0, pageable);
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

    @PostMapping()
    public String createProduct(@Valid @ModelAttribute("product") ProductDTO product,
            BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) throws IOException {
        
        if (product.getImageFile().isEmpty()) {
            bindingResult.addError(new FieldError("product", "imageFile", "Please choose an image"));
        }
        if (bindingResult.hasErrors()) {
            List<CategoryDTO> categories = categoryService.findAll();
            model.addAttribute("categories", categories);
            bindingResult.addError(new FieldError("product", "imageFile", "Please choose an image"));
            return "/seller/product/addOrEdit";
        }
        
        if (product.getId() != null) {
            redirectAttributes.addFlashAttribute("message", "Success! Your product was updated.");
            productAPI.updateProduct(product, product.getId());
        } else {
            redirectAttributes.addFlashAttribute("message", "Success! Your product was published.");
            productAPI.saveProduct(product);
        }

        return "redirect:/seller/product/list/all?page=1&size=2";
    }
    
    @GetMapping()
    public String newProduct(Model model) {
        model.addAttribute("product", new ProductDTO());
        List<CategoryDTO> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        return "seller/product/addOrEdit";
    }

    @GetMapping("/{id}")
    public String editProduct(
            @PathVariable("id") Long id, Model model
    ) throws IOException, SerialException, SQLException {

        ProductDTO productDTO = productService.findById(id);

        List<CategoryDTO> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("product", productDTO);

        return "/seller/product/addOrEdit";
    }
}
