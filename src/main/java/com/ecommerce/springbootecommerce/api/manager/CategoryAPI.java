package com.ecommerce.springbootecommerce.api.manager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.ecommerce.springbootecommerce.dto.CategoryDTO;
import com.ecommerce.springbootecommerce.service.ICategoryService;

@RestController
@RequestMapping(value = "/api/manager")
public class CategoryAPI {
    
    @Autowired
    private ICategoryService categoryService;
    
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/imagedata/manager";

    
    @PostMapping(value = "/category")
    public RedirectView createCategory(
            @ModelAttribute(name = "category") CategoryDTO category,
            @RequestParam(value = "imageField") MultipartFile file
        ) throws IOException {
        byte[] imageBytes = file.getBytes();
        
        String author = SecurityContextHolder.getContext().getAuthentication().getName();
        
        String fileName= file.getOriginalFilename();
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

        category.setThumbnail(imageBytes);
        category.setThumbnailBase64(imageBytes.toString());
        categoryService.save(category);
        
        return new RedirectView("/manager/category?page=1&size=2");
    }

}
