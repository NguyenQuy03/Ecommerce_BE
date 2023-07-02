package com.ecommerce.springbootecommerce.service.impl;

import com.ecommerce.springbootecommerce.converter.ProductConverter;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.entity.AccountEntity;
import com.ecommerce.springbootecommerce.entity.CategoryEntity;
import com.ecommerce.springbootecommerce.entity.ProductEntity;
import com.ecommerce.springbootecommerce.repository.AccountRepository;
import com.ecommerce.springbootecommerce.repository.CategoryRepository;
import com.ecommerce.springbootecommerce.repository.ProductRepository;
import com.ecommerce.springbootecommerce.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public void save(ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();

        if (productDTO.getId() != null) {
            ProductEntity preProductEntity = productRepository.findOneById(productDTO.getId());
            if (productDTO.getImage() == null) {
                productDTO.setImage(preProductEntity.getImage());                
            }
            productEntity = productConverter.toEntity(productDTO, preProductEntity);
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();

            productEntity = productConverter.toEntity(productDTO);
            CategoryEntity categoryEntity = categoryRepository.findOneById(productDTO.getCategoryId());
            AccountEntity accountEntity = accountRepository.findByUsername(currentPrincipalName).get();

            productEntity.setCategory(categoryEntity);
            productEntity.setAccount(accountEntity);

        }

        productRepository.save(productEntity);
    }

    @Override
    public void softDelete(String status, long[] ids) {
        for (long id : ids) {
            productRepository.softDelete(status, id);
        }
    }
    
    @Override
    public void forceDelete(String status, long[] ids) {
        for (long id : ids) {
            productRepository.softDelete(status, id);
        }
    }
    
    @Override
    public void save(ProductEntity entity) {
        productRepository.save(entity);
    }
    
    @Override
    public void restore(long id) {
        productRepository.restore(id);
    }
    
    //FIND
    @Override
    public ProductDTO findByAccountIdAndId(long accountId, long id) {
        Optional<ProductEntity> res = productRepository.findByAccountIdAndId(accountId, id);
        boolean isExist = res.isPresent();
        ProductDTO productDTO = null;
        if (isExist) {
            ProductEntity productEntity = res.get();
            productDTO = productConverter.toDTO(productEntity);
        }
        return productDTO;
    }
    
    @Override
    public List<ProductDTO> findAll(Pageable pageable) {
        List<ProductEntity> listProductEntity = productRepository.findAll(pageable).getContent();
        return productConverter.toListProductDTO(listProductEntity);
    }

    @Override
    public List<ProductDTO> findAllByAccountId(long accountId, Pageable pageable) {
        List<ProductEntity> listProductEntity = productRepository.findAllByAccountId(accountId, pageable).getContent();
        return productConverter.toListProductDTO(listProductEntity);
    }

    @Override
    public List<ProductDTO> findAllByCategoryId(long categoryId, Pageable pageable) {
        List<ProductEntity> listProductEntity = productRepository.findAllByCategoryId(categoryId, pageable).getContent();
        return productConverter.toListProductDTO(listProductEntity);
    }

    @Override
    public List<ProductDTO> findByStockEqualsAndAccountIdAndStatusNotAndStatusNot(long stock, Long accountId, String ignoreStatus1, String ignoreStatus2, Pageable pageable) {
        List<ProductEntity> listProductEntity = productRepository.findByStockEqualsAndAccountIdAndStatusNotAndStatusNot(stock, accountId, ignoreStatus1, ignoreStatus2, pageable).getContent();
        return productConverter.toListProductDTO(listProductEntity);
    }

    @Override
    public List<ProductDTO> findByStockGreaterThanAndAccountIdAndStatusNotAndStatusNot(long stock, Long accountId, String ignoreStatus1, String ignoreStatus2, Pageable pageable) {
        List<ProductEntity> listProductEntity = productRepository.findByStockGreaterThanAndAccountIdAndStatusNotAndStatusNot(stock, accountId, ignoreStatus1, ignoreStatus2, pageable).getContent();
        return productConverter.toListProductDTO(listProductEntity);
    }

    @Override
    public ProductDTO findById(Long id) {
        ProductEntity productEntity = productRepository.findOneById(id);
        return productConverter.toDTO(productEntity);
    }

    @Override
    public List<ProductDTO> findAllByNameContains(String keyword, Pageable pageable) {
        List<ProductEntity> productEntities = productRepository.findAllByNameContains(keyword, pageable).getContent();
        return productConverter.toListProductDTO(productEntities);
    }

    @Override
    public List<ProductDTO> findAllByStatus(String status, Pageable pageable) {
        List<ProductEntity> productEntities = productRepository.findAllByStatus(status, pageable).getContent();
        return productConverter.toListProductDTO(productEntities);
    }

    
    @Override
    public List<ProductDTO> findAllBySellerNameOrderBySoldDesc(String sellerName) {
        List<ProductEntity> productEntities = productRepository.findAllByCreatedByOrderBySoldDesc(sellerName).getContent();
        return productConverter.toListProductDTO(productEntities);
    }
    
    @Override
    public List<ProductDTO> findAllByAccountIdAndStatus(Long id, String status, Pageable pageable) {
        List<ProductEntity> productEntities = productRepository.findAllByAccountIdAndStatus(id, status).getContent();
        return productConverter.toListProductDTO(productEntities);
    }
    
    @Override
    public List<ProductDTO> findAllByAccountIdAndStatusNotAndStatusNot(long id, String ignoreStatus1, String ignoreStatus2, Pageable pageable) {
        List<ProductEntity> productEntities = productRepository.findAllByAccountIdAndStatusNotAndStatusNot(id, ignoreStatus1, ignoreStatus2, pageable).getContent();
        return productConverter.toListProductDTO(productEntities);
    }
    
    //COUNT
    @Override
    public long countAllByStatus(String status) {
        return productRepository.countAllByStatus(status);
    }

    @Override
    public long countAllByAccountIdAndStatus(long accountId, String status) {
        return productRepository.countAllByAccountIdAndStatus(accountId, status);
    }
    
    @Override
    public long countAllByCategoryId(Long categoryId) {
        return productRepository.countAllByCategoryId(categoryId);
    }

    @Override
    public long countByNameContains(String keyword) {
        return productRepository.countByNameContains(keyword);
    }

    @Override
    public long countAllByAccountId(long accountId) {
        return productRepository.countAllByAccountId(accountId);
    }

    @Override
    public long countByStockGreaterThanAndAccountIdAndStatusNotAndStatusNot(long stock, Long accountId, String ignoreStatus1, String ignoreStatus2){
        return productRepository.countByStockGreaterThanAndAccountIdAndStatusNotAndStatusNot(stock, accountId, ignoreStatus1, ignoreStatus2);
    }

    @Override
    public long countByStockEqualsAndAccountIdAndStatusNotAndStatusNot(long stock, Long accountId, String ignoreStatus1, String ignoreStatus2){
        return productRepository.countByStockEqualsAndAccountIdAndStatusNotAndStatusNot(stock, accountId, ignoreStatus1, ignoreStatus2);
    }

    @Override
    public long countAllByAccountIdAndStatusNotAndStatusNot(long id, String ignoreStatus1, String ignoreStatus2) {
        return productRepository.countAllByAccountIdAndStatusNotAndStatusNot(id, ignoreStatus1, ignoreStatus2);
    }

    
    //EXIST
    @Override
    public boolean isProductExistByIdAndStatusNot(Long id, String ignoreStatus) {
        return productRepository.findOneByIdAndStatusNot(id, ignoreStatus).isPresent();
    }

}
