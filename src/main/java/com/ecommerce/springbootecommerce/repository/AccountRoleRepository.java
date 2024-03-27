package com.ecommerce.springbootecommerce.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.springbootecommerce.entity.AccountRoleEntity;
import com.ecommerce.springbootecommerce.entity.AccountRoleEntity.AccountRoleEntityId;

@Repository
public interface AccountRoleRepository extends CrudRepository<AccountRoleEntity, AccountRoleEntityId> {
    AccountRoleEntity findByAccountId(Long accountId);

    List<AccountRoleEntity> findAllByRoleCodeIn(Set<String> roleCodes);

    List<AccountRoleEntity> findByAccountIdAndRoleCode(Long id, String roleCode);

    List<AccountRoleEntity> findAllByAccountId(Long id);

    int deleteByAccountId(Long accountId);
}
