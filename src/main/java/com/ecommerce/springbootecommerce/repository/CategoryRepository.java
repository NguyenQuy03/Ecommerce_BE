package com.ecommerce.springbootecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.springbootecommerce.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findOneByCode(String code);

    Optional<CategoryEntity> findOneByIdAndAccountId(long id, long accountId);

    List<CategoryEntity> findAllByAccountId(long accountId);
    
    Page<CategoryEntity> findAllByAccountId(long accounId, Pageable pageable);
}
