package com.ecommerce.springbootecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.springbootecommerce.entity.Account.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    AccountEntity findOneByUserNameAndStatus(String username, Boolean status);
}
