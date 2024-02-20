package com.ecommerce.springbootecommerce.util.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.entity.ProductEntity;
import com.ecommerce.springbootecommerce.entity.ProductItemEntity;
import com.ecommerce.springbootecommerce.repository.ProductItemRepository;
import com.ecommerce.springbootecommerce.util.converter.account_role.AccountConverter;

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
        dto.setSpecification(entity.getSpecification());
        dto.setVariational(entity.isVariational());
        dto.setImage(entity.getImage());
        dto.setStatus(entity.getStatus());

        List<ProductItemEntity> productItemEntities = productItemRepo.findAllByProductId(entity.getId());
        dto.setProductItems(productItemConverter.toListDTO(productItemEntities));

        Map<String, Object> productDetails = getProductDetails(productItemEntities);
        dto.setAdditionalInfo(productDetails);

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
        for (ProductEntity entity : listProductEntity) {
            List<ProductItemEntity> productItemEntities = productItemRepo.findAllByProductId(entity.getId());
            ProductDTO productDTO = toDTO(entity);
            productDTO.setProductItems(productItemConverter.toListDTO(productItemEntities));
            productDTOs.add(productDTO);
        }
        return productDTOs;
    }

    // REUSE
    private Map<String, Object> getProductDetails(List<ProductItemEntity> productItemEntities) {
        Map<String, Object> details = new HashMap<>();
        AtomicLong totalSold = new AtomicLong(0);
        AtomicReference<Double> maxPrice = new AtomicReference<>(Double.MIN_VALUE);
        AtomicReference<Double> minPrice = new AtomicReference<>(Double.MAX_VALUE);
        AtomicLong totalStock = new AtomicLong(0);
        AtomicReference<Double> revenue = new AtomicReference<>(0D);

        if (productItemEntities.size() == 1) {
            details.put("totalSold", productItemEntities.get(0).getSold());
            details.put("totalStock", productItemEntities.get(0).getStock());
            details.put("avgPrice", "$" + productItemEntities.get(0).getPrice());

            return details;
        } else {
            productItemEntities.forEach(item -> {
                totalSold.addAndGet(item.getSold());
                totalStock.addAndGet(item.getStock());
                revenue.updateAndGet(v -> v + item.getSold() * item.getPrice());

                if (item.getPrice() > maxPrice.get()) {
                    maxPrice.set(item.getPrice());
                }
                if (item.getPrice() < minPrice.get()) {
                    minPrice.set(item.getPrice());
                }
            });
        }

        details.put("totalSold", totalSold.get());
        details.put("totalStock", totalStock.get());
        details.put("revenue", revenue.get());

        if (minPrice.get().equals(maxPrice.get())) {
            details.put("avgPrice", "$" + minPrice);
        } else {
            details.put("avgPrice", "$" + minPrice + " - " + "$" + maxPrice);
        }

        return details;
    }
}
