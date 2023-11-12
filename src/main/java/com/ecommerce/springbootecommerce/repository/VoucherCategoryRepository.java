package com.ecommerce.springbootecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.springbootecommerce.entity.VoucherCategoryEntity;
import com.ecommerce.springbootecommerce.entity.VoucherCategoryEntity.VoucherCategoryEntityId;

@Repository
public interface VoucherCategoryRepository extends JpaRepository<VoucherCategoryEntity, VoucherCategoryEntityId>{

    
}
