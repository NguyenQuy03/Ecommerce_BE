package com.ecommerce.springbootecommerce.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.constant.StatusConstant;
import com.ecommerce.springbootecommerce.dto.BaseDTO;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.dto.ProductItemDTO;
import com.ecommerce.springbootecommerce.entity.CategoryEntity;
import com.ecommerce.springbootecommerce.entity.ProductEntity;
import com.ecommerce.springbootecommerce.repository.CategoryRepository;
import com.ecommerce.springbootecommerce.repository.ProductItemRepository;
import com.ecommerce.springbootecommerce.repository.ProductRepository;
import com.ecommerce.springbootecommerce.service.IProductService;
import com.ecommerce.springbootecommerce.util.ProductServiceUtil;
import com.ecommerce.springbootecommerce.util.ServiceUtil;
import com.ecommerce.springbootecommerce.util.converter.ProductConverter;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private ProductItemRepository productItemRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private ProductItemService productItemService;

    @Autowired
    private ProductConverter productConverter;

    @Autowired
    private ProductServiceUtil productServiceUtil;

    @Autowired
    private ServiceUtil serviceUtil;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void save(ProductDTO dto) {           
        try {
            dto.setStatus(StatusConstant.STRING_ACTIVE_STATUS);                      

            ExecutorService executorService = Executors.newFixedThreadPool(5);
                String imageUrl = productServiceUtil.updateProductImage(dto);
                dto.setImage(imageUrl);
                
                if(dto.getProductItems().get(0).getVariationName() != null) {
                    List<Future<String>> uploadProductItemImages = productServiceUtil.productItemImages(dto.getProductItems(), executorService);

                    for (int i = 0; i < uploadProductItemImages.size(); i++) {
                        try {
                            String productItemImageUrl = uploadProductItemImages.get(i).get();
                            dto.getProductItems().get(i).setImage(productItemImageUrl);
                        } catch (Exception e) {
                            throw new RuntimeException("Error store product image");
                        }
                    }
                }
            executorService.shutdown();

            ProductEntity entity = productConverter.toEntity(dto);
            CategoryEntity categoryEntity = categoryRepo.findOneByCode(dto.getCategory().getCode()).get();
            entity.setCategory(categoryEntity);

            dto.setId(productRepo.save(entity).getId());

            // Save All Product Items
            productItemService.saveAll(dto, entity);
        
        } catch (Exception e) {
            for(ProductItemDTO productItemDTO : dto.getProductItems()) {
                productItemRepo.deleteById(productItemDTO.getId());
            }
            productRepo.deleteById(dto.getId());
            throw new RuntimeException("Error occurred, product creation rolled back");
        }
    }

    @Override
    public void update(ProductDTO dto) {
            // Get Previous Product Version
        ProductEntity preProductEntity = productRepo.findById(dto.getId())
            .orElseThrow(() -> new IllegalArgumentException("Product is not exist"));
            
        dto.setStatus(StatusConstant.STRING_ACTIVE_STATUS);      
        
            // Filter Product Items Do Not Valid
        productItemService.filterUnUsedProductItem(dto);

        ExecutorService executorService = Executors.newFixedThreadPool(5);
            String imageUrl = productServiceUtil.updateProductImage(dto);
            dto.setImage(imageUrl);
            
            if(dto.getProductItems().get(0).getVariationName() != null) {
                List<Future<String>> uploadProductItemImages = productServiceUtil.productItemImages(dto.getProductItems(), executorService);

                for (int i = 0; i < uploadProductItemImages.size(); i++) {
                    try {
                        String productItemImageUrl = uploadProductItemImages.get(i).get();
                        dto.getProductItems().get(i).setImage(productItemImageUrl);
                    } catch (Exception e) {
                        throw new RuntimeException("Error store image");
                    }
                }
            }
        executorService.shutdown();

        preProductEntity = productConverter.toEntity(dto, preProductEntity);
        CategoryEntity categoryEntity = categoryRepo.findOneByCode(dto.getCategory().getCode()).get();
        preProductEntity.setCategory(categoryEntity);

        try {
            productRepo.save(preProductEntity);
        } catch (Exception e) {
            throw new RuntimeException("Error save product");
        }

            // Update Product Items       
        productItemService.saveAll(dto, preProductEntity);
    }

    @Override
    public void softDelete(String status, long[] ids) {
        for (Long id : ids) {
            ProductEntity product = productRepo.findById(id).get();
            product.setStatus(status);
            productRepo.save(product);
        }
    }

    @Override
    public void forceDelete(String status, long[] ids) {
        softDelete(status, ids);
    }

    @Override
    public void restore(long id) {
        Optional<ProductEntity> optionalProduct = productRepo.findById(id);
        if(optionalProduct.isPresent()) {
            ProductEntity product = optionalProduct.get();
            product.setStatus(StatusConstant.STRING_ACTIVE_STATUS);
            productRepo.save(product);
        }
    }
    @Override
    public void save(ProductEntity entity) {
        productRepo.save(entity);
    }

    //FIND
    @Override
    public ProductDTO findById(long id) {
        return productRepo.findById(id).map(item -> productConverter.toDTO(item)).orElse(null);
    }

    @Override
    public ProductDTO findOneByIdAndAccountId(long id, long accountId) {
        return productRepo.findOneByIdAndAccountId(id, accountId).map(item -> productConverter.toDTO(item)).orElse(null);
    }

    @Override
    public List<ProductDTO> findAllByCategoryId(long categoryId, Pageable pageable) {
        List<ProductEntity> listProductEntity = productRepo.findAllByCategoryId(categoryId, pageable).getContent();
        return productConverter.toListDTO(listProductEntity);
    }

    @Override
    public List<ProductDTO> findAllByNameContains(String keyword, Pageable pageable) {
        List<ProductEntity> productEntities = productRepo.findAllByNameContains(keyword, pageable).getContent();
        return productConverter.toListDTO(productEntities);
    }

    @Override
    public List<ProductDTO> findAllByStatus(String status, Pageable pageable) {
        List<ProductEntity> productEntities = productRepo.findAllByStatus(status, pageable).getContent();
        return productConverter.toListDTO(productEntities);
    }

    @Override
    public List<ProductDTO> findAllByAccountIdAndStatus(long accountId, String status) {
        List<ProductEntity> productEntities = productRepo.findAllByAccountIdAndStatus(accountId, status);
        return productConverter.toListDTO(productEntities);
    }
    
    @Override
    public BaseDTO<ProductDTO> findAllByAccountIdAndStatus(long accountId, String status, int page, int size) {
        Page<ProductEntity> pageEntity = productRepo.findAllByAccountIdAndStatus(accountId, status, PageRequest.of(page - 1, size));
        BaseDTO<ProductDTO> res = serviceUtil.mapDataFromPage(pageEntity);
        res.setListResult(productConverter.toListDTO(pageEntity.getContent()));
        return res;
    }
    
    @Override
    public BaseDTO<ProductDTO> findAllByAccountIdAndProductStatusAndProductItemStatus(
        long accountId, String productStatus, String productItemStatus,
        int page, int size
    ) {
        Page<ProductEntity> pageEntity = productRepo.findAllByAccountIdAndProductStatusAndProductItemStatus(
            accountId, productStatus, productItemStatus, PageRequest.of(page - 1, size)
        );
        BaseDTO<ProductDTO> res = serviceUtil.mapDataFromPage(pageEntity);
        res.setListResult(productConverter.toListDTO(pageEntity.getContent()));
        return res;
    }

    @Override
    public ProductDTO findTopSelling(String sellerName) {
        Page<ProductEntity> productEntities = productRepo.findTopSelling(PageRequest.of(0, 20));

        ProductDTO dto = new ProductDTO();
        dto.setListResult(productConverter.toListDTO(productEntities.getContent()));

        return dto;
    }
    
    //COUNT
    @Override
    public long countAllByStatus(String status) {
        return productRepo.countAllByStatus(status);
    }
    
    @Override
    public long countAllByCategoryId(long categoryId) {
        return productRepo.countAllByCategoryId(categoryId);
    }

    @Override
    public long countByNameContains(String keyword) {
        return productRepo.countByNameContains(keyword);
    }

}
