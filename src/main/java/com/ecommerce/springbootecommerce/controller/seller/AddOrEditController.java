package com.ecommerce.springbootecommerce.controller.seller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ecommerce.springbootecommerce.api.seller.ProductAPI;
import com.ecommerce.springbootecommerce.dto.CategoryDTO;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.service.ICategoryService;
import com.ecommerce.springbootecommerce.service.IProductService;

@Controller
@RequestMapping("seller/product")
public class AddOrEditController {

    @Autowired
    private ProductAPI productAPI;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @PostMapping()
    public String createProduct(@Valid @ModelAttribute("product") ProductDTO product,
            BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) throws IOException {
        
        if (product.getImageFile().isEmpty()) {
            bindingResult.addError(new FieldError("product", "imageFile", "Please choose an image"));
        }
        
        if (bindingResult.hasErrors()) {
            List<CategoryDTO> categories = categoryService.findAll();
            model.addAttribute("categories", categories);
            return "/seller/product/addOrEdit";
        }
        
        if (product.getId() != null) {
            redirectAttributes.addFlashAttribute("message", "Success! Your product was updated.");
            productAPI.update(product, product.getId());
        } else {
            redirectAttributes.addFlashAttribute("message", "Success! Your product was published.");
            productAPI.save(product);
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

        ProductDTO product = productService.findById(id);
        
        byte[] inputArray = product.getImage();
        MultipartFile multipartFile = new MockMultipartFile("tempFileName", inputArray);
        
        product.setImageFile(multipartFile);

        List<CategoryDTO> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("product", product);

        return "/seller/product/addOrEdit";
    }
}
