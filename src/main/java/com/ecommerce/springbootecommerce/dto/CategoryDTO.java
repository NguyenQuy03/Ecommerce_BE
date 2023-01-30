package com.ecommerce.springbootecommerce.dto;

import java.util.Base64;
import java.util.Set;

import com.ecommerce.springbootecommerce.entity.ProductEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO extends BaseDTO<CategoryDTO> {  
    
    private String name;
    private String code;
    private byte[] thumbnail;
    private String thumbnailBase64;
    private Set<ProductEntity> products;

    public String getThumbnailBase64() {
        thumbnailBase64 = Base64.getEncoder().encodeToString(this.thumbnail);
        return thumbnailBase64;
    }
    
    public void setThumbnailBase64(String thumbnailBase64) {
        this.thumbnailBase64 = thumbnailBase64;
    }
}
