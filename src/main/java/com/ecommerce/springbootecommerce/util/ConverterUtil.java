package com.ecommerce.springbootecommerce.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.dto.product.ProductDTO;
import com.ecommerce.springbootecommerce.dto.product.ProductItemDTO;
import com.ecommerce.springbootecommerce.entity.ProductEntity;
import com.ecommerce.springbootecommerce.entity.ProductItemEntity;

@Component
public class ConverterUtil {
    @Autowired
    private ModelMapper mapper;
    
    public ProductDTO toProductDTO(ProductEntity entity) {
        ProductDTO dto = new ProductDTO();

        dto.setId(entity.getId());
        dto.setCategoryId(entity.getCategoryId());
        dto.setAccountId(entity.getAccountId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setSpecification(entity.getSpecification());
        dto.setProductItems(toListProductItemDTO(entity.getProductItems()));
        dto.setImage(entity.getImage());
        dto.setStatus(entity.getStatus());

        
        Map<String, Object> productDetails = getProductDetails(entity);
        dto.setTotalSold((long) productDetails.get("totalSold"));
        dto.setTotalStock((long) productDetails.get("totalStock"));
        dto.setAvgPrice((String) productDetails.get("avgPrice"));
        dto.setVariations((Map<String, List<String>>) productDetails.get("variations"));       

        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setModifiedBy(entity.getModifiedBy());

        return dto;
    }

    public ProductEntity toProductEntity(ProductDTO dto) {
        ProductEntity entity = new ProductEntity();
        entity.setCategoryId(dto.getCategoryId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        entity.setSpecification(dto.getSpecification());
        
        if(dto.getAccountId() != null)
            entity.setAccountId(dto.getAccountId());
        if(dto.getId() != null) 
            entity.setId(dto.getId());
        if(dto.getImage() != null) 
            entity.setImage(dto.getImage());

        List<ProductItemEntity> itemEntities = new ArrayList<>();

        for(Map<String, String> itemData : dto.getProductItemsData()) {
            ProductItemEntity itemEntity = new ProductItemEntity();
            itemEntity.setPrice(Double.parseDouble(itemData.get("price")));
            itemEntity.setStock(Long.parseLong(itemData.get("stock")));
            itemEntity.setVariationName(itemData.get("variationKey"));
            itemEntity.setVariationValue(itemData.get("variationValue"));

            itemEntity.setImage(itemData.get("image"));
            
            itemEntities.add(itemEntity);
        }

        entity.setProductItems(itemEntities);
        return entity;
    }

    public ProductItemDTO toProductItemDTO(ProductItemEntity entity) {
        ProductItemDTO dto = mapper.map(entity, ProductItemDTO.class);
        return dto;
    }

    public List<ProductItemDTO> toListProductItemDTO(List<ProductItemEntity> entities){
        List<ProductItemDTO> dtos = new ArrayList<>();
        for(ProductItemEntity entity : entities) {
            dtos.add(toProductItemDTO(entity));
        }
        return dtos;
    }

    private Map<String, Object> getProductDetails(ProductEntity entity) {
        Map<String, Object> details = new HashMap<>();
        AtomicLong totalSold = new AtomicLong(0);
        AtomicReference<Double> maxPrice = new AtomicReference<>(Double.MIN_VALUE);
        AtomicReference<Double> minPrice = new AtomicReference<>(Double.MAX_VALUE);
        Map<String, List<String>> variations = new HashMap<>();
        AtomicLong totalStock = new AtomicLong(0);

        if(entity.getProductItems().size() == 1) {
            details.put("totalSold", entity.getProductItems().get(0).getSold());
            details.put("totalStock", entity.getProductItems().get(0).getStock());
            details.put("avgPrice", "$" + entity.getProductItems().get(0).getPrice());
            details.put("variations", variations);

            return details;
        } else {
            entity.getProductItems().forEach(item -> {
                totalSold.addAndGet(item.getSold());
                totalStock.addAndGet(item.getStock());
    
                if (item.getPrice() > maxPrice.get()) {
                    maxPrice.set(item.getPrice());
                }
                if (item.getPrice() < minPrice.get()) {
                    minPrice.set(item.getPrice());
                }
    
                if (variations.isEmpty() || !variations.containsKey(item.getVariationName())) {
                    variations.put(item.getVariationName(), new LinkedList<>(Arrays.asList(item.getVariationValue())));
                } else {
                    variations.get(item.getVariationName()).add(item.getVariationValue());
                }
            });
        }


        details.put("totalSold", totalSold.get());
        details.put("totalStock", totalStock.get());
        details.put("avgPrice", "$" + minPrice + " - " + "$" + maxPrice);
        details.put("variations", variations);

        return details;
    }
    
}
