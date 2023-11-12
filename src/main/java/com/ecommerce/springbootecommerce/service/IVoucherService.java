package com.ecommerce.springbootecommerce.service;

import java.util.List;

import com.ecommerce.springbootecommerce.constant.enums.voucher.VoucherStatus;
import com.ecommerce.springbootecommerce.dto.BaseDTO;
import com.ecommerce.springbootecommerce.dto.VoucherDTO;

public interface IVoucherService {

    void save(VoucherDTO dto);

    void update(VoucherDTO dto);

    BaseDTO<VoucherDTO> findAllByAccountId(long accountId, int page, int size);

    BaseDTO<VoucherDTO> findAllByAccountIdAndStatus(long accountId, VoucherStatus status, int page, int size);
    
    List<VoucherDTO> findAllByAccountIdAndStatus(long accountId, VoucherStatus status);

    VoucherDTO findOneByIdAndAccountId(long id, long accountId);

    boolean isExistByCodeAndAccountId(String code, long accountId);

}
