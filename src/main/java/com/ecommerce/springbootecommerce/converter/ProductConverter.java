package com.ecommerce.springbootecommerce.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.entity.ProductEntity;

@Component
public class ProductConverter {

    public ProductEntity toEntity(ProductDTO product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(product.getName());
        productEntity.setPrice(product.getPrice());
        productEntity.setDescription(product.getDescription());
        productEntity.setStock(product.getStock());
        productEntity.setImage(product.getImage());
        return productEntity;
    }

    public ProductDTO toDTO(ProductEntity entity) {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(entity.getId());
        productDTO.setName(entity.getName());
        productDTO.setPrice(entity.getPrice());
        productDTO.setStock(entity.getStock());
        productDTO.setDescription(entity.getDescription());
        productDTO.setImage(entity.getImage());

        productDTO.setCreatedDate(entity.getCreatedDate());
        productDTO.setCreatedBy(entity.getCreatedBy());
        productDTO.setModifiedDate(entity.getMordifiedDate());
        productDTO.setModifiedBy(entity.getMordifiedBy());

        return productDTO;
    }

    public ProductEntity toEntity(ProductDTO product, ProductEntity productEntity) {
        productEntity.setName(product.getName());
        productEntity.setPrice(product.getPrice());
        productEntity.setStock(product.getStock());
        productEntity.setDescription(product.getDescription());
        productEntity.setImage(product.getImage());
        return productEntity;
    }

    public List<ProductDTO> toListProductDTO(List<ProductEntity> listProductEntity) {
        List<ProductDTO> listProductDTO = new ArrayList<>();
        for (ProductEntity productEntity : listProductEntity) {
            ProductDTO productDTO = toDTO(productEntity);
            listProductDTO.add(productDTO);
        }
        return listProductDTO;
    }
    
}
