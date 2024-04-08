package com.ecommerce.springbootecommerce.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.constant.enums.product.ProductStatus;
import com.ecommerce.springbootecommerce.constant.enums.voucher.VoucherStatus;
import com.ecommerce.springbootecommerce.dto.BaseDTO;
import com.ecommerce.springbootecommerce.dto.VoucherDTO;
import com.ecommerce.springbootecommerce.entity.CategoryEntity;
import com.ecommerce.springbootecommerce.entity.ProductEntity;
import com.ecommerce.springbootecommerce.entity.VoucherCategoryEntity;
import com.ecommerce.springbootecommerce.entity.VoucherEntity;
import com.ecommerce.springbootecommerce.entity.VoucherProductEntity;
import com.ecommerce.springbootecommerce.repository.CategoryRepository;
import com.ecommerce.springbootecommerce.repository.ProductRepository;
import com.ecommerce.springbootecommerce.repository.VoucherCategoryRepository;
import com.ecommerce.springbootecommerce.repository.VoucherProductRepository;
import com.ecommerce.springbootecommerce.repository.VoucherRepository;
import com.ecommerce.springbootecommerce.service.IVoucherService;
import com.ecommerce.springbootecommerce.util.ServiceUtil;
import com.ecommerce.springbootecommerce.util.converter.VoucherConverter;

@Service
public class VoucherService implements IVoucherService {

    @Autowired
    private VoucherRepository voucherRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private VoucherProductRepository voucherProductRepo;

    @Autowired
    private VoucherCategoryRepository voucherCategoryRepo;

    @Autowired
    private ServiceUtil serviceUtil;

    @Autowired
    private VoucherConverter voucherConverter;

    @Override
    @Transactional
    public void save(VoucherDTO dto) {
        dto.setStatusByDate(new Date());

        VoucherEntity newVoucher = voucherRepo.save(voucherConverter.toEntity(dto));

        if (dto.getAccount().getMainRole().equals(SystemConstant.ROLE_MANAGER)) {
            List<CategoryEntity> categories = categoryRepo.findAllByAccountId(dto.getAccount().getId());
            List<VoucherCategoryEntity> listEntities = new ArrayList<>();
            for (CategoryEntity catEntity : categories) {
                var entity = new VoucherCategoryEntity();

                entity.setCategory(catEntity);
                entity.setVoucher(newVoucher);
                listEntities.add(entity);
            }

            voucherCategoryRepo.saveAll(listEntities);
        } else if (dto.getAccount().getMainRole().equals(SystemConstant.ROLE_SELLER)) {
            List<ProductEntity> products = productRepo.findAllByAccountIdAndStatus(dto.getAccount().getId(),
                    ProductStatus.LIVE);
            List<VoucherProductEntity> listEntities = new ArrayList<>();
            for (ProductEntity productEntity : products) {
                var entity = new VoucherProductEntity();
                entity.setProduct(productEntity);
                entity.setVoucher(newVoucher);

                listEntities.add(entity);
            }

            voucherProductRepo.saveAll(listEntities);
        }
    }

    @Override
    @Transactional
    public void update(VoucherDTO dto) {
        Optional<VoucherEntity> optionalEntity = voucherRepo.findOneByIdAndAccountId(dto.getId(),
                dto.getAccount().getId());
        if (optionalEntity.isPresent()) {
            dto.setStatusByDate(new Date());

            VoucherEntity newEntity = voucherConverter.toEntity(dto, optionalEntity.get());

            voucherRepo.save(newEntity);
        } else {
            throw new RuntimeException("Voucher is not exist");
        }
    }

    // FIND PAGE
    @Override
    public BaseDTO<VoucherDTO> findAllByAccountId(long accountId, int page, int size) {
        Page<VoucherEntity> pageEntities = voucherRepo.findAllByAccountId(accountId, PageRequest.of(page, size));
        BaseDTO<VoucherDTO> dto = serviceUtil.mapDataFromPage(pageEntities);
        dto.setListResult(voucherConverter.toListDTO(pageEntities.getContent()));

        return dto;
    }

    @Override
    public BaseDTO<VoucherDTO> findAllByAccountIdAndStatus(long accountId, VoucherStatus status, int page, int size) {
        Page<VoucherEntity> pageEntities = voucherRepo.findAllByAccountIdAndStatus(accountId, status,
                PageRequest.of(page, size));
        BaseDTO<VoucherDTO> dto = serviceUtil.mapDataFromPage(pageEntities);
        dto.setListResult(voucherConverter.toListDTO(pageEntities.getContent()));

        return dto;
    }

    // FIND ONE
    @Override
    public VoucherDTO findOneByIdAndAccountId(long id, long accountId) {
        return voucherRepo.findOneByIdAndAccountId(id, accountId)
                .map(item -> voucherConverter.toDTO(item)).orElse(null);
    }

    @Override
    public boolean isExistByCodeAndAccountId(String code, long accountId) {
        return voucherRepo.findOneByCodeAndAccountId(code, accountId).isPresent();
    }

    // FIND LIST
    @Override
    public List<VoucherDTO> findAllByAccountIdAndStatus(long accountId, VoucherStatus status) {

        List<VoucherEntity> entities = voucherRepo.findAllByAccountIdAndStatus(accountId, status);
        return voucherConverter.toListDTO(entities);
    }

}
