package com.ecommerce.springbootecommerce.repository;

import com.ecommerce.springbootecommerce.entity.CategoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<CategoryEntity, String> {
    CategoryEntity findOneById(String id);
}
