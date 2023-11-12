package com.ecommerce.springbootecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.springbootecommerce.entity.VoucherProductEntity;
import com.ecommerce.springbootecommerce.entity.VoucherProductEntity.VoucherProductEntityId;

@Repository
public interface VoucherProductRepository extends JpaRepository<VoucherProductEntity, VoucherProductEntityId>{

    
}
