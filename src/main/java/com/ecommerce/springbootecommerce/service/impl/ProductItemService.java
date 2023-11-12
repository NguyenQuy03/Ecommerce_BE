package com.ecommerce.springbootecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.constant.StatusConstant;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.dto.ProductItemDTO;
import com.ecommerce.springbootecommerce.entity.ProductEntity;
import com.ecommerce.springbootecommerce.entity.ProductItemEntity;
import com.ecommerce.springbootecommerce.repository.ProductItemRepository;
import com.ecommerce.springbootecommerce.service.IProductItemService;
import com.ecommerce.springbootecommerce.util.converter.ProductItemConverter;

@Service
public class ProductItemService implements IProductItemService {
    @Autowired
    private ProductItemRepository productItemRepo;
    
    @Autowired
    private ProductItemConverter productItemConverter;

    @Override
    public List<ProductItemDTO> findAllByStatus(String status, Pageable pageable) {
        List<ProductItemEntity> entities = productItemRepo.findAllByStatus(status, pageable).getContent();
        return productItemConverter.toListDTO(entities);
    }

    @Override
    public ProductItemDTO findOneById(Long id) {
        Optional<ProductItemEntity> entity = productItemRepo.findById(id);
        return entity.map(item -> productItemConverter.toDTO(item)).orElse(null);
    }

    @Override
    public List<ProductItemDTO> findTopSelling(String sellerName) {
        return productItemConverter.toListDTO(productItemRepo.findAllByCreatedByOrderBySoldDesc(sellerName));
    }

    @Override
    public void saveAll(ProductDTO productDTO, ProductEntity productEntity) {
        for(ProductItemDTO dto : productDTO.getProductItems()) {
            if(dto.getId() == null) {
                save(dto, productDTO, productEntity);
            } else {
                update(dto, productEntity);
            }
        }
    }

    @Override
    public void save(ProductItemDTO dto, ProductDTO productDTO, ProductEntity productEntity) {
        ProductItemEntity productItemEntity = productItemConverter.toEntity(dto);
            productItemEntity.setProduct(productEntity);
            productItemEntity.setStatus(StatusConstant.STRING_ACTIVE_STATUS);
            if(productItemEntity.getImage() == null) {
                productItemEntity.setImage(productDTO.getImage());
            }

            productItemRepo.save(productItemEntity);
    }

    @Override
    public void update(ProductItemDTO dto, ProductEntity productEntity) {
        ProductItemEntity preProductItemEntity = productItemRepo.findById(dto.getId()).get();
        ProductItemEntity itemEntity = productItemConverter.toEntity(preProductItemEntity, dto);
        itemEntity.setProduct(productEntity);
        
        if(itemEntity.getStock() > 0) {
            itemEntity.setStatus(StatusConstant.STRING_ACTIVE_STATUS);
        } else {
            itemEntity.setStatus(StatusConstant.SOLD_OUT_STATUS);
        }

        productItemRepo.save(itemEntity);
    }

    @Override
    public void filterUnUsedProductItem(ProductDTO productDTO) {
        List<ProductItemEntity> preProductItems = productItemRepo.findAllByProductId(productDTO.getId());
        List<Long> preProductItemIds = new ArrayList<>();
        preProductItems.forEach(item -> {
            preProductItemIds.add(item.getId());
        });

        for(ProductItemDTO itemDTO : productDTO.getProductItems()) {
            if(itemDTO.getId() == null) continue;
            
            for(int i = 0; i < preProductItemIds.size(); i++) {
                if(itemDTO.getId().equals(preProductItemIds.get(i))) {
                    preProductItemIds.remove(i);
                    break;
                }
                if(!itemDTO.getId().equals(preProductItemIds.get(i)) && i == preProductItemIds.size() - 1) {
                    productItemRepo.deleteById(itemDTO.getId());
                    productDTO.getProductItems().remove(itemDTO);
                }
            }
        }
    }
}
    