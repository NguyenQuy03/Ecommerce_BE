package com.ecommerce.springbootecommerce.service.impl;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.dto.ProductDTO;
import com.ecommerce.springbootecommerce.entity.AccountEntity;
import com.ecommerce.springbootecommerce.entity.CategoryEntity;
import com.ecommerce.springbootecommerce.entity.ProductEntity;
import com.ecommerce.springbootecommerce.repository.AccountRepository;
import com.ecommerce.springbootecommerce.repository.CategoryRepository;
import com.ecommerce.springbootecommerce.repository.ProductRepository;
import com.ecommerce.springbootecommerce.service.IProductService;
import com.ecommerce.springbootecommerce.util.ConverterUtil;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
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
    private ModelMapper modelMapper;

    @Autowired
    private ConverterUtil converterUtil;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(ProductDTO productDTO) {
        ProductEntity productEntity;

        String partSeparator = ",";
        if (productDTO.getImageBase64Data().contains(partSeparator)) {
            String encodedImg = productDTO.getImageBase64Data().split(partSeparator)[1];
            byte[] decodedImg = Base64.getDecoder().decode(encodedImg.getBytes(StandardCharsets.UTF_8));
            productDTO.setImage(new Binary(BsonBinarySubType.BINARY, decodedImg));
        }

        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();

        productEntity = modelMapper.map(productDTO, ProductEntity.class);
        CategoryEntity categoryEntity = categoryRepository.findOneById(String.valueOf(productDTO.getCategoryId()));
        AccountEntity accountEntity = accountRepository.findByUsername(currentPrincipalName).get();

        productEntity.setCategoryId(categoryEntity.getId());
        productEntity.setAccountId(accountEntity.getId());

        productRepository.save(productEntity);
    }

    @Override
    public void update(ProductDTO productDTO) {
        ProductEntity preProductEntity = productRepository.findOneById(productDTO.getId()).get();
        String productImageBase64 = productDTO.getImageBase64Data();

        if (productImageBase64 != null && !productImageBase64.equals("")) {
            String partSeparator = ",";
            if (productImageBase64.contains(partSeparator)) {
                String encodedImg = productImageBase64.split(partSeparator)[1];
                byte[] decodedImg = Base64.getDecoder().decode(encodedImg.getBytes(StandardCharsets.UTF_8));
                productDTO.setImage(new Binary(BsonBinarySubType.BINARY, decodedImg));
            }
        } else {
            productDTO.setImage(preProductEntity.getImage());
        }
        if (productDTO.getStock() > 0) {
            productDTO.setStatus(SystemConstant.STRING_ACTIVE_STATUS);
        }
        modelMapper.map(productDTO, preProductEntity);
        ProductEntity productEntity = modelMapper.map(preProductEntity, ProductEntity.class);

        productRepository.save(productEntity);
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
        return product.map(productEntity -> modelMapper.map(productEntity, ProductDTO.class))
                .orElse(null);
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
    public List<ProductDTO> findAllByAccountIdAndStatus(String accountId, String status, Pageable pageable) {
        Criteria criteria = new Criteria();
        criteria.and("accountId").is(accountId);
        criteria.andOperator(Criteria.where("status").is(status));
        Query query = new Query(criteria).with(pageable);
        List<ProductEntity> results = mongoTemplate.find(query, ProductEntity.class);

        return toListProductDTO(results);
    }
    
    @Override
    public List<ProductDTO> findAllValid(String accountId, String ignoreStatus1, String ignoreStatus2, Pageable pageable) {
        Criteria criteria = new Criteria();
        criteria.and("accountId").is(accountId);
        criteria.andOperator(Criteria.where("status").ne(ignoreStatus1), Criteria.where("status").ne(ignoreStatus2));
        Query query = new Query(criteria).with(pageable);
        List<ProductEntity> results = mongoTemplate.find(query, ProductEntity.class);

        return toListProductDTO(results);
    }

    @Override
    public List<ProductDTO> findAllSoldOut(long stock, String accountId, String ignoreStatus1, String ignoreStatus2, Pageable pageable) {
        Criteria criteria = new Criteria();
        criteria.and("accountId").is(accountId);
        criteria.and("stock").equals(stock);
        criteria.andOperator(Criteria.where("status").ne(ignoreStatus1), Criteria.where("status").ne(ignoreStatus2));
        Query query = new Query(criteria).with(pageable);
        List<ProductEntity> results = mongoTemplate.find(query, ProductEntity.class);

        return toListProductDTO(results);
    }

    @Override
    public List<ProductDTO> findAllLive(long stock, String accountId, String ignoreStatus1, String ignoreStatus2, Pageable pageable) {
        Criteria criteria = new Criteria();
        criteria.and("accountId").is(accountId);
        criteria.and("stock").gt(stock);
        criteria.andOperator(Criteria.where("status").ne(ignoreStatus1), Criteria.where("status").ne(ignoreStatus2));
        Query query = new Query(criteria).with(pageable);
        List<ProductEntity> results = mongoTemplate.find(query, ProductEntity.class);

        return toListProductDTO(results);
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

    @Override
    public long countAllLive(long stock, String accountId, String ignoreStatus1, String ignoreStatus2){
        Criteria criteria = new Criteria();
        criteria.and("accountId").is(accountId);
        criteria.and("stock").gt(stock);
        criteria.andOperator(Criteria.where("status").ne(ignoreStatus1), Criteria.where("status").ne(ignoreStatus2));
        Query query = new Query(criteria);
        return mongoTemplate.count(query, ProductEntity.class);
    }

    @Override
    public long countAllSoldOut(long stock, String accountId, String ignoreStatus1, String ignoreStatus2){
        Criteria criteria = new Criteria();
        criteria.and("accountId").is(accountId);
        criteria.and("stock").equals(stock);
        criteria.andOperator(Criteria.where("status").ne(ignoreStatus1), Criteria.where("status").ne(ignoreStatus2));
        Query query = new Query(criteria);
        return mongoTemplate.count(query, ProductEntity.class);
    }

    @Override
    public long countAllValid(String accountId, String ignoreStatus1, String ignoreStatus2) {
        Criteria criteria = new Criteria();
        criteria.and("accountId").is(accountId);
        criteria.andOperator(Criteria.where("status").ne(ignoreStatus1), Criteria.where("status").ne(ignoreStatus2));
        Query query = new Query(criteria);
        return mongoTemplate.count(query, ProductEntity.class);
    }

    /* REUSE */
    private List<ProductDTO> toListProductDTO(List<ProductEntity> listProductEntity) {
        List<ProductDTO> listProductDTO = new ArrayList<>();
        for(ProductEntity entity : listProductEntity) {
            listProductDTO.add(converterUtil.toProductDTO(entity));
        }
        return listProductDTO;
    }

}
