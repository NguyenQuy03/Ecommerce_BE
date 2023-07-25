package com.ecommerce.springbootecommerce.dto;

import com.ecommerce.springbootecommerce.entity.ProductEntity;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;

import java.util.Base64;
import java.util.Set;

@Getter
@Setter
public class CategoryDTO extends BaseDTO<CategoryDTO> {  
    
    private String code;
    private Binary thumbnail;
    private String thumbnailBase64;
    private Set<ProductEntity> products;

    public String getThumbnailBase64() {
        thumbnailBase64 = Base64.getEncoder().encodeToString(this.thumbnail.getData());
        return thumbnailBase64;
    }
    
    public void setThumbnailBase64(String thumbnailBase64) {
        this.thumbnailBase64 = thumbnailBase64;
    }
}
