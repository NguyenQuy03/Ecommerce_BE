package com.ecommerce.springbootecommerce.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.springbootecommerce.entity.AccountRoleEntity;

@Repository
public interface AccountRoleRepository
        extends CrudRepository<AccountRoleEntity, AccountRoleEntity.AccountRoleEntityId> {
    
    AccountRoleEntity findByAccountId(Long accountId);
    
    Set<AccountRoleEntity> findAllByRoleCode(String roleCode);

    Set<AccountRoleEntity> findByAccountIdAndRoleCode(Long id, String roleCode);

    Set<AccountRoleEntity> findAllByAccountId(Long id);
}
