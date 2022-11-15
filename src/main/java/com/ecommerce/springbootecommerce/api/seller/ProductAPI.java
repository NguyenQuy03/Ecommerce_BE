package com.ecommerce.springbootecommerce.api.seller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.service.IProductService;


@RestController
@RequestMapping("/api/seller")
public class ProductAPI {
    
    @Autowired
    private IProductService productService;

    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/imagedata/seller";
    
    @PostMapping(value="/product")
    public RedirectView createProduct(
        @ModelAttribute(name = "product") ProductDTO product,
        @RequestParam(value = "imageField") MultipartFile file
    ) throws IOException{
        byte[] imageBytes = file.getBytes();
        
        String author = SecurityContextHolder.getContext().getAuthentication().getName();
        
        String fileName= file.getOriginalFilename();
        Path fileNameAndPath = Paths.get(uploadDirectory, author + "_" + fileName);
        
        try {
            Files.write(fileNameAndPath, imageBytes);

        } catch (Exception e) {
            e.printStackTrace();            
        }

        product.setImage(imageBytes);
        product.setImageBase64(imageBytes.toString());
        productService.save(product);
        
        return new RedirectView("/seller/product/list/all?page=1&size=2");
    }
    
    @PutMapping(value="/product/{id}")
    public ProductDTO updateProduct(
            @RequestBody ProductDTO model, 
            @PathVariable("id") Long id,
            @RequestParam("imageField") MultipartFile file
    ) {
        model.setId(id);
        return productService.save(model);
    }
    
    @DeleteMapping(value="/product")
    public void deleteProduct(@RequestBody long[] ids) {
        productService.delete(ids);
    }
    
}
