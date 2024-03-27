package com.ecommerce.springbootecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.ecommerce.springbootecommerce.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findOneByCode(String code);

    Optional<CategoryEntity> findOneByIdAndAccountId(Long id, Long accountId);

    @Modifying
    Long deleteById(long id);

    List<CategoryEntity> findAllByAccountId(Long accountId);

    Page<CategoryEntity> findAllByAccountId(Long accountId, Pageable pageable);
}
