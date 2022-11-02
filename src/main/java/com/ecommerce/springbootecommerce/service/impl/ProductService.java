package com.ecommerce.springbootecommerce.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.converter.ProductConverter;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.entity.AccountEntity;
import com.ecommerce.springbootecommerce.entity.CategoryEntity;
import com.ecommerce.springbootecommerce.entity.ProductEntity;
import com.ecommerce.springbootecommerce.repository.AccountRepository;
import com.ecommerce.springbootecommerce.repository.CategoryRepository;
import com.ecommerce.springbootecommerce.repository.ProductRepository;
import com.ecommerce.springbootecommerce.service.IProductService;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private ProductConverter productConverter;

    @Override
    public ProductDTO save(ProductDTO productDTO) {   
        ProductEntity productEntity = new ProductEntity();
        
        if (productDTO.getId() != null) {
            ProductEntity preProductEntity = productRepository.findOneById(productDTO.getId());
            productEntity = productConverter.toEntity(productDTO, preProductEntity);
        } else {
            productEntity = productConverter.toEntity(productDTO);            
        }
        
        CategoryEntity categoryEntity = categoryRepository.findOneByCode(productDTO.getCategoryCode());
        AccountEntity accountEntity = accountRepository.findOneById(productDTO.getAccountId());
        
        productEntity.setCategory(categoryEntity);
        productEntity.setAccount(accountEntity);

        productRepository.save(productEntity);
        return productConverter.toDTO(productEntity);
    }
}
