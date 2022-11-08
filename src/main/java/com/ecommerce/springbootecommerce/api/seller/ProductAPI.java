package com.ecommerce.springbootecommerce.api.seller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.service.IProductService;


@RestController()
@RequestMapping("/api/seller")
public class ProductAPI {
    
    @Autowired
    private IProductService productService;

    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/imagedata";
    
    @PostMapping(value="/product")
    public String createProduct(
        @ModelAttribute(name = "product") ProductDTO product,
        @RequestParam(value = "imageField") MultipartFile file
    ) throws IOException{
        byte[] imageBytes = file.getBytes();
        
        String fileName= file.getOriginalFilename();
        Path fileNameAndPath = Paths.get(uploadDirectory, fileName);
        
        try {
            Files.write(fileNameAndPath, imageBytes);

        } catch (Exception e) {
            e.printStackTrace();            
        }

        product.setImage(imageBytes);
        product.setBase64Image(imageBytes.toString());
        productService.save(product);
        
        return "redirect:/seller/product/list";
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
