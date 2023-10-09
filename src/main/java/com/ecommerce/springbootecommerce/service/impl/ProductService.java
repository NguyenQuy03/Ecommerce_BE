package com.ecommerce.springbootecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.product.ProductDTO;
import com.ecommerce.springbootecommerce.dto.product.ProductItemDTO;
import com.ecommerce.springbootecommerce.entity.CategoryEntity;
import com.ecommerce.springbootecommerce.entity.ProductEntity;
import com.ecommerce.springbootecommerce.entity.ProductItemEntity;
import com.ecommerce.springbootecommerce.repository.CategoryRepository;
import com.ecommerce.springbootecommerce.repository.ProductItemRepository;
import com.ecommerce.springbootecommerce.repository.ProductRepository;
import com.ecommerce.springbootecommerce.service.IProductService;
import com.ecommerce.springbootecommerce.util.ProductServiceUtil;
import com.ecommerce.springbootecommerce.util.converter.ProductConverter;
import com.ecommerce.springbootecommerce.util.converter.ProductItemConverter;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private ProductItemRepository productItemRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private ProductConverter productConverter;

    @Autowired
    private ProductItemConverter productItemConverter;

    @Autowired
    private ProductServiceUtil productServiceUtil;

    @Override
    public void save(ProductDTO productDTO) {           
        try {
            productDTO.setStatus(SystemConstant.STRING_ACTIVE_STATUS);                      

            ExecutorService executorService = Executors.newFixedThreadPool(5);
                String imageUrl = productServiceUtil.updateProductImage(productDTO);
                productDTO.setImage(imageUrl);
                
                if(productDTO.getProductItems().get(0).getVariationName() != null) {
                    List<Future<String>> uploadProductItemImages = productServiceUtil.productItemImages(productDTO.getProductItems(), executorService);

                    for (int i = 0; i < uploadProductItemImages.size(); i++) {
                        try {
                            String productItemImageUrl = uploadProductItemImages.get(i).get();
                            productDTO.getProductItems().get(i).setImage(productItemImageUrl);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            executorService.shutdown();

            ProductEntity entity = productConverter.toEntity(productDTO);
            CategoryEntity categoryEntity = categoryRepo.findOneByCode(productDTO.getCategory().getCode()).get();
            entity.setCategory(categoryEntity);

            try {
                productRepo.save(entity);
            } catch (Exception e) {
                throw new RuntimeException("Error create product");
            }

            for(ProductItemDTO productItemDTO : productDTO.getProductItems()) {
                ProductItemEntity productItemEntity = productItemConverter.toEntity(productItemDTO);
                productItemEntity.setProduct(entity);
                productItemEntity.setStatus(SystemConstant.STRING_ACTIVE_STATUS);
                if(productItemEntity.getImage() == null) {
                    productItemEntity.setImage(productDTO.getImage());
                }
                try {
                    productItemRepo.save(productItemEntity);
                } catch (Exception e) {
                    productItemRepo.delete(productItemEntity);
                    throw new RuntimeException("Error create product item");
                }
            }
            

        } catch (Exception e) {
            for(ProductItemDTO productItemDTO : productDTO.getProductItems()) {
                productItemRepo.deleteById(productItemDTO.getId());
            }
            throw new RuntimeException("Error occurred, product creation rolled back");
        }
    }

    @Override
    public void update(ProductDTO dto) {
            // Get Previous Product Version
        ProductEntity preProductEntity = productRepo.findById(dto.getId())
            .orElseThrow(() -> new IllegalArgumentException("Product is not exist"));
            
        dto.setStatus(SystemConstant.STRING_ACTIVE_STATUS);      
        
        List<ProductItemEntity> preProductItems = productItemRepo.findAllByProductId(dto.getId());
        List<Long> preProductItemIds = new ArrayList<>();
        preProductItems.forEach(item -> {
            preProductItemIds.add(item.getId());
        });

        for(ProductItemDTO itemDTO : dto.getProductItems()) {
            if(itemDTO.getId() == null) continue;
            
            for(int i = 0; i < preProductItemIds.size(); i++) {
                if(itemDTO.getId().equals(preProductItemIds.get(i))) {
                    preProductItemIds.remove(i);
                    break;
                }
                if(!itemDTO.getId().equals(preProductItemIds.get(i)) && i == preProductItemIds.size() - 1) {
                    productItemRepo.deleteById(itemDTO.getId());
                    dto.getProductItems().remove(itemDTO);
                }
            }
        }

        if(preProductItemIds.size() > 0) {
            productItemRepo.deleteAllById(preProductItemIds);
        }

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
                        e.printStackTrace();
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

        for(ProductItemDTO itemDTO : dto.getProductItems()) {
            if(itemDTO.getStock() > 0) {
                itemDTO.setStatus(SystemConstant.STRING_ACTIVE_STATUS);
            } else {
                itemDTO.setStatus(SystemConstant.SOLD_OUT_STATUS);     
            }  
        }

            // Update Product Item            
        for(ProductItemDTO productItemDTO : dto.getProductItems()) {
            try {
                if(productItemDTO.getId() == null) {
                    ProductItemEntity itemEntity = productItemConverter.toEntity(productItemDTO);
                    itemEntity.setProduct(preProductEntity);
                    productItemRepo.save(itemEntity);
                } else {
                    ProductItemEntity preProductItemEntity = productItemRepo.findById(productItemDTO.getId()).get();
                    ProductItemEntity itemEntity = productItemConverter.toEntity(preProductItemEntity, productItemDTO);
                    itemEntity.setProduct(preProductEntity);
                    productItemRepo.save(itemEntity);
                }
            } catch (Exception e) {
                System.out.println(e);
                throw new RuntimeException("Error save product item");
            }
        }
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
            product.setStatus(SystemConstant.STRING_ACTIVE_STATUS);
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
    public ProductDTO findAllByAccountIdAndStatus(long accountId, String status, int page, int size) {
        Page<ProductEntity> pageEntity = productRepo.findAllByAccountIdAndStatus(accountId, status, PageRequest.of(page - 1, size));
        return productConverter.mapDataFromPage(pageEntity);
    }
    
    @Override
    public ProductDTO findAllByAccountIdAndProductStatusAndProductItemStatus(
        long accountId, String productStatus, String productItemStatus,
        int page, int size
    ) {
        Page<ProductEntity> pageEntity = productRepo.findAllByAccountIdAndProductStatusAndProductItemStatus(
            accountId, productStatus, productItemStatus, PageRequest.of(page - 1, size)
        );
        return productConverter.mapDataFromPage(pageEntity);
    }

    @Override
    public ProductDTO findTopSelling(String sellerName) {
        ProductDTO dto = new ProductDTO();
        Page<ProductEntity> pageDTO = productRepo.findTopSelling(PageRequest.of(0, 25));


        return null;
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
