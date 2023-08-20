package com.ecommerce.springbootecommerce.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.BaseDTO;
import com.ecommerce.springbootecommerce.dto.product.ProductDTO;
import com.ecommerce.springbootecommerce.entity.AccountEntity;
import com.ecommerce.springbootecommerce.entity.CategoryEntity;
import com.ecommerce.springbootecommerce.entity.ProductEntity;
import com.ecommerce.springbootecommerce.entity.ProductItemEntity;
import com.ecommerce.springbootecommerce.repository.AccountRepository;
import com.ecommerce.springbootecommerce.repository.CategoryRepository;
import com.ecommerce.springbootecommerce.repository.ProductItemRepository;
import com.ecommerce.springbootecommerce.repository.ProductRepository;
import com.ecommerce.springbootecommerce.service.IProductService;
import com.ecommerce.springbootecommerce.util.ConverterUtil;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ConverterUtil converterUtil;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(ProductDTO productDTO) {         
        ProductEntity productEntity = converterUtil.toProductEntity(productDTO);

        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();

        CategoryEntity categoryEntity = categoryRepository.findOneById(String.valueOf(productDTO.getCategoryId()));
        AccountEntity accountEntity = accountRepository.findByUsername(currentPrincipalName).get();

        String customProductId = "";
        boolean isUnique = false;
        while (!isUnique) {
            customProductId = UUID.randomUUID().toString();
            if (!productRepository.existsById(customProductId)) {
                productEntity.setId(customProductId);
                isUnique = true;
            }
        }
        
        productEntity.setId(customProductId);
        productEntity.setStatus(SystemConstant.STRING_ACTIVE_STATUS);

        productEntity.setCategoryId(categoryEntity.getId());
        productEntity.setAccountId(accountEntity.getId());
        List<ProductItemEntity> productItems = new ArrayList<>();

        for(ProductItemEntity productItem : productEntity.getProductItems()) {
            productItem.setProductId(customProductId);
            productItems.add(productItemRepository.save(productItem));
        }

        productEntity.setProductItems(productItems);

        productRepository.save(productEntity);

    }

    @Override
    public void update(ProductDTO dto) {
        ProductEntity preProductEntity = productRepository.findOneById(dto.getId()).get();

        for(int i = 0; i < preProductEntity.getProductItems().size(); i++) {
            ProductItemEntity productItem = preProductEntity.getProductItems().get(i);
            boolean isChange = false;
        
            if(dto.getProductItemsData().get(i).get("image") != productItem.getImage()) {
                isChange = true;
            }
            
            if(productItem.getStatus() == SystemConstant.INACTIVE_PRODUCT && productItem.getStock() > 0) {
                productItem.setStatus(SystemConstant.STRING_ACTIVE_STATUS);
                isChange = true;
            } else if(productItem.getStatus() == SystemConstant.STRING_ACTIVE_STATUS && productItem.getStock() == 0) {
                productItem.setStatus(SystemConstant.INACTIVE_PRODUCT);     
                isChange = true;
            }
            
            if(isChange)
                productItemRepository.save(productItem);
        }

        dto.setAccountId(preProductEntity.getAccountId());
        preProductEntity = converterUtil.toProductEntity(dto);

        productRepository.save(preProductEntity);
    }

    @Override
    public void softDelete(String status, String[] ids) {
        for (String id : ids) {
            ProductEntity product = productRepository.findOneById(id).get();
            product.setStatus(status);
            productRepository.save(product);
        }
    }

    @Override
    public void forceDelete(String status, String[] ids) {
        softDelete(status, ids);
    }

    @Override
    public void restore(String id) {
        Optional<ProductEntity> optionalProduct = productRepository.findOneById(id);
        if(optionalProduct.isPresent()) {
            ProductEntity product = optionalProduct.get();
            product.setStatus(SystemConstant.STRING_ACTIVE_STATUS);
            productRepository.save(product);
        }
    }
    @Override
    public void save(ProductEntity entity) {

        productRepository.save(entity);
    }

    //FIND
    @Override
    public ProductDTO findByAccountIdAndId(String accountId, String id) {
        Optional<ProductEntity> res = productRepository.findByAccountIdAndId(accountId, id);
        return res.map(productEntity -> modelMapper.map(productEntity, ProductDTO.class))
                .orElse(null);
    }

    @Override
    public List<ProductDTO> findAllByCategoryId(String categoryId, Pageable pageable) {
        List<ProductEntity> listProductEntity = productRepository.findAllByCategoryId(categoryId, pageable).getContent();
        return toListProductDTO(listProductEntity);
    }

    @Override
    public ProductDTO findOneById(String id) {
        Optional<ProductEntity> product = productRepository.findOneById(id);
        if(product.isPresent()) {
            return converterUtil.toProductDTO(product.get());
        }
        return null;
    }

    @Override
    public List<ProductDTO> findAllByNameContains(String keyword, Pageable pageable) {
        List<ProductEntity> productEntities = productRepository.findAllByNameContains(keyword, pageable).getContent();
        return toListProductDTO(productEntities);
    }

    @Override
    public List<ProductDTO> findAllByStatus(String status, Pageable pageable) {
        List<ProductEntity> productEntities = productRepository.findAllByStatus(status, pageable).getContent();
        return toListProductDTO(productEntities);
    }

    
    @Override
    public List<ProductDTO> findTopSelling(String accountId) {
        SortOperation sort = Aggregation.sort(Sort.Direction.DESC, "sold");
        MatchOperation match = Aggregation.match(Criteria.where("accountId").is(accountId));
        TypedAggregation<ProductEntity> aggregation = Aggregation.newAggregation(ProductEntity.class, match, sort);

        List<ProductEntity> result = mongoTemplate.aggregate(aggregation, ProductEntity.class).getMappedResults();
        return toListProductDTO(result);
    }
    
    @Override
    public BaseDTO<ProductDTO> findAllByAccountIdAndStatus(String accountId, String status, int page, int size) {
        Page<ProductEntity> curPage = productRepository.findAllByAccountIdAndStatus(
            accountId, status,
            PageRequest.of(page - 1, size)
        );

        return getPagableData(curPage, page, size);
    }
    
    @Override
    public BaseDTO<ProductDTO> findAllValid(String accountId, String ignoreStatus1, String ignoreStatus2, int page, int size) {
        Page<ProductEntity> curPage = productRepository.findAllValid(
            accountId, Arrays.asList(ignoreStatus1, ignoreStatus2),
            PageRequest.of(page - 1, size)
        );

        return getPagableData(curPage, page, size);
    }
    
    @Override
    public BaseDTO<ProductDTO> findAllLive(String accountId, String ignoreStatus1, String ignoreStatus2, long stock, int page, int size) {
        Page<ProductEntity> curPage = productRepository.findAllLive(
            accountId, Arrays.asList(ignoreStatus1, ignoreStatus2),
            stock, PageRequest.of(page - 1, size)
        );
        
        return getPagableData(curPage, page, size);
    }

    @Override
    public BaseDTO<ProductDTO> findAllSoldOut(String accountId, String ignoreStatus1, String ignoreStatus2, long stock, int page, int size) {
        Page<ProductEntity> curPage = productRepository.findSoldOut(
            accountId, Arrays.asList(ignoreStatus1, ignoreStatus2),
            stock, PageRequest.of(page - 1, size)
        );

        return getPagableData(curPage, page, size);
    }
    
    //COUNT
    @Override
    public long countAllByStatus(String status) {
        return productRepository.countAllByStatus(status);
    }

    @Override
    public long countAllByAccountIdAndStatus(String accountId, String status) {
        return productRepository.countAllByAccountIdAndStatus(accountId, status);
    }
    
    @Override
    public long countAllByCategoryId(String categoryId) {
        return productRepository.countAllByCategoryId(categoryId);
    }

    @Override
    public long countByNameContains(String keyword) {
        return productRepository.countByNameContains(keyword);
    }
    
    /* REUSE */
    private List<ProductDTO> toListProductDTO(List<ProductEntity> listProductEntity) {
        List<ProductDTO> listProductDTO = new ArrayList<>();
        for(ProductEntity entity : listProductEntity) {
            listProductDTO.add(converterUtil.toProductDTO(entity));
        }
        return listProductDTO;
    }

    private BaseDTO<ProductDTO> getPagableData(Page<ProductEntity> curPage, int page, int size) {
        BaseDTO<ProductDTO> dto = new BaseDTO<>();
        dto.setListResult(toListProductDTO(curPage.getContent()));
        dto.setTotalItem(curPage.getTotalElements());
        dto.setPage(page);
        dto.setSize(size);
        dto.setTotalPage(curPage.getTotalPages());

        return dto;
    }

}
