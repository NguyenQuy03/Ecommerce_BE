package com.ecommerce.springbootecommerce.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.springbootecommerce.entity.AccountRoleEntity;
import com.ecommerce.springbootecommerce.entity.AccountRoleEntity.AccountRoleEntityId;

@Repository
public interface AccountRoleRepository extends CrudRepository<AccountRoleEntity, AccountRoleEntityId>{
    AccountRoleEntity findByAccountId(Long accountId);
    
    Set<AccountRoleEntity> findAllByRoleCode(String roleCode);

    Set<AccountRoleEntity> findByAccountIdAndRoleCode(Long id, String roleCode);

    Set<AccountRoleEntity> findAllByAccountId(Long id);
}
