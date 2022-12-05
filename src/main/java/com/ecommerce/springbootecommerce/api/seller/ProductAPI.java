package com.ecommerce.springbootecommerce.api.seller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.service.IProductService;

@RestController
@RequestMapping("/api/seller")
public class ProductAPI {
    
    @Autowired  
    private IProductService productService;

    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/imagedata/seller";
    
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
    
    @PostMapping(value="/product")
    public void saveProduct(
        ProductDTO product
    ) throws IOException{
        byte[] imageBytes = product.getImageFile().getBytes();
        String author = SecurityContextHolder.getContext().getAuthentication().getName();
        
        String fileName= product.getImageFile().getOriginalFilename();
        Path storePath = Paths.get(uploadDirectory, author);
        Path fileNameAndPath = Paths.get(uploadDirectory + "/" + author, fileName);
        
        File directoryStorePath = new File(String.valueOf(storePath));
        File directoryFileNamePath = new File(String.valueOf(fileNameAndPath));
                
        if(!directoryStorePath.exists()) {    
            Files.createDirectories(storePath);
        } 
        
        if(!directoryFileNamePath.exists()) {    
            Files.write(fileNameAndPath, imageBytes);
        }

        product.setImage(imageBytes);
        product.setStatus(SystemConstant.ACTIVE_PRODUCT);
        productService.save(product);
        
    }
    
    @PutMapping(value="/product/{id}")
    public void updateProduct(
            @RequestBody ProductDTO product,
            @PathVariable("id") Long id
    ) throws IOException {
        
        String author = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!product.getImageFile().isEmpty()) {
                byte[] imageBytes = product.getImageFile().getBytes();
                String fileName= product.getImageFile().getOriginalFilename();
                Path storePath = Paths.get(uploadDirectory, author);
                Path fileNameAndPath = Paths.get(uploadDirectory + "/" + author, fileName);
                
                File directoryStorePath = new File(String.valueOf(storePath));
                File directoryFileNamePath = new File(String.valueOf(fileNameAndPath));
                
                if(!directoryStorePath.exists()) {    
                    Files.createDirectories(storePath);
                } 
                
                if(!directoryFileNamePath.exists()) {    
                    Files.write(fileNameAndPath, product.getImage());
                }
                
                product.setImage(imageBytes);
        }
        
        product.setId(id);
        product.setModifiedBy(author);
        product.setModifiedDate(new Date());
        productService.save(product);
    }
    
    @DeleteMapping(value="/product")
    public void deleteProduct(@RequestBody long[] ids) {
        productService.delete(ids);
    }
    
}
