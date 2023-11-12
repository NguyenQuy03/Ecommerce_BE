package com.ecommerce.springbootecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.springbootecommerce.constant.enums.voucher.VoucherStatus;
import com.ecommerce.springbootecommerce.entity.VoucherEntity;

@Repository
public interface VoucherRepository extends JpaRepository<VoucherEntity, Long>{

    Optional<VoucherEntity> findOneByIdAndAccountId(long id, long accountId);

    Optional<VoucherEntity> findOneByCodeAndAccountId(String code, long accountId);

    Page<VoucherEntity> findAllByAccountId(long accountId, Pageable pageable);

    Page<VoucherEntity> findAllByAccountIdAndStatus(long accountId, VoucherStatus status, Pageable pageable);

    List<VoucherEntity> findAllByAccountIdAndStatus(long accountId, VoucherStatus status);
}
