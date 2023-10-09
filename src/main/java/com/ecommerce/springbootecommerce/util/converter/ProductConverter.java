package com.ecommerce.springbootecommerce.util.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.dto.product.ProductDTO;
import com.ecommerce.springbootecommerce.entity.ProductEntity;
import com.ecommerce.springbootecommerce.entity.ProductItemEntity;
import com.ecommerce.springbootecommerce.repository.ProductItemRepository;

@Component
public class ProductConverter {

    @Autowired
    private CategoryConverter categoryConverter;

    @Autowired
    private AccountConverter accountConverter;

    @Autowired
    private ProductItemConverter productItemConverter;

    @Autowired
    private ProductItemRepository productItemRepo;
    
    public ProductDTO toDTO(ProductEntity entity) {
        ProductDTO dto = new ProductDTO();

        dto.setId(entity.getId());
        dto.setCategory(categoryConverter.toDTO(entity.getCategory()));
        dto.setAccount(accountConverter.toDTO(entity.getAccount()));
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setSpecification(convertStringToMap(entity.getSpecification()));
        dto.setImage(entity.getImage());
        dto.setStatus(entity.getStatus());

        List<ProductItemEntity> productItemEntities = productItemRepo.findAllByProductId(entity.getId());
        dto.setProductItems(productItemConverter.toListDTO(productItemEntities));

        Map<String, Object> productDetails = getProductDetails(productItemEntities);
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

    public ProductEntity toEntity(ProductDTO dto) {
        ProductEntity entity = new ProductEntity();
        entity.setAccount(accountConverter.toEntity(dto.getAccount()));
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        entity.setSpecification(dto.getSpecification().toString());
        
        entity.setImage(dto.getImage());
        
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        return entity;
    }

    public ProductEntity toEntity(ProductDTO dto, ProductEntity preProductEntity) {
        ProductEntity preEntity = preProductEntity;
        preEntity.setName(dto.getName());
        preEntity.setDescription(dto.getDescription());
        preEntity.setStatus(dto.getStatus());
        preEntity.setSpecification(dto.getSpecification().toString());
        preEntity.setImage(dto.getImage());

        return preEntity;
    }
    
    public List<ProductDTO> toListDTO(List<ProductEntity> listProductEntity) {
        List<ProductDTO> productDTOs = new ArrayList<>();
        for(ProductEntity entity : listProductEntity) {
            List<ProductItemEntity> productItemEntities = productItemRepo.findAllByProductId(entity.getId());
            ProductDTO productDTO = toDTO(entity);
            productDTO.setProductItems(productItemConverter.toListDTO(productItemEntities));
            productDTOs.add(productDTO);
        }
        return productDTOs;
    }

    public ProductDTO mapDataFromPage(Page<ProductEntity> pageEntity) {
        ProductDTO dto = new ProductDTO();
        dto.setListResult(toListDTO(pageEntity.getContent()));
        dto.setTotalItem(pageEntity.getTotalElements());
        dto.setTotalPage(pageEntity.getTotalPages());
        dto.setPage((int) pageEntity.getPageable().getPageNumber() + 1);
        dto.setSize(pageEntity.getPageable().getPageSize());
        return dto;
    }

    // REUSE
    private Map<String, Object> getProductDetails(List<ProductItemEntity> productItemEntities) {
        Map<String, Object> details = new HashMap<>();
        AtomicLong totalSold = new AtomicLong(0);
        AtomicReference<Double> maxPrice = new AtomicReference<>(Double.MIN_VALUE);
        AtomicReference<Double> minPrice = new AtomicReference<>(Double.MAX_VALUE);
        Map<String, List<String>> variations = new HashMap<>();
        AtomicLong totalStock = new AtomicLong(0);

        if(productItemEntities.size() == 1) {
            details.put("totalSold", productItemEntities.get(0).getSold());
            details.put("totalStock", productItemEntities.get(0).getStock());
            details.put("avgPrice", "$" + productItemEntities.get(0).getPrice());
            details.put("variations", variations);

            return details;
        } else {
            productItemEntities.forEach(item -> {
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

    private Map<String, String> convertStringToMap(String mapString) {
        Map<String, String> map = new HashMap<>();

        String[] entries = mapString.substring(1, mapString.length() - 1).split(", ");
        for(String entry : entries) {
            String[] keyValue = entry.split("=");
            if(keyValue.length == 2) {
                map.put(keyValue[0], keyValue[1]);
            }
        }

        return map;
    }

}
