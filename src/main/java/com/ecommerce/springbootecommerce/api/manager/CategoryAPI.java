package com.ecommerce.springbootecommerce.api.manager;

import com.ecommerce.springbootecommerce.dto.CategoryDTO;
import com.ecommerce.springbootecommerce.service.ICategoryService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping(value = "/api/manager")
public class CategoryAPI {
    
    @Autowired
    private ICategoryService categoryService;
    
    @PostMapping(value = "/category")
    public RedirectView createCategory(
            @ModelAttribute(name = "category") CategoryDTO category,
            @RequestParam(value = "imageField") MultipartFile file
        ) throws IOException {

        byte[] imageBytes = file.getBytes();

        category.setThumbnail(new Binary(BsonBinarySubType.BINARY, imageBytes));
        category.setThumbnailBase64(Arrays.toString(imageBytes));
        category.setId(null);
        categoryService.save(category);
        return new RedirectView("/manager/category?page=1&size=2");
    }

}
