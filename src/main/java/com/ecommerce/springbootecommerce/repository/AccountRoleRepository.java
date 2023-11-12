package com.ecommerce.springbootecommerce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.springbootecommerce.entity.AccountRoleEntity;
import com.ecommerce.springbootecommerce.entity.AccountRoleEntity.AccountRoleEntityId;

@Repository
public interface AccountRoleRepository extends CrudRepository<AccountRoleEntity, AccountRoleEntityId>{
    AccountRoleEntity findByAccountId(Long accountId);
    
    List<AccountRoleEntity> findAllByRoleCode(String roleCode);

    List<AccountRoleEntity> findByAccountIdAndRoleCode(Long id, String roleCode);

    List<AccountRoleEntity> findAllByAccountId(Long id);
}
