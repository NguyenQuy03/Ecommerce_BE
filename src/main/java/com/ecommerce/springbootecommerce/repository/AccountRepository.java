package com.ecommerce.springbootecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.springbootecommerce.entity.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    AccountEntity findOneByUserNameAndStatus(String username, Boolean status);
    AccountEntity findOneByUserName(String username);
    AccountEntity findOneById(Long id);
    
    Optional<AccountEntity> findByEmail(String email);
    Optional<AccountEntity> findByUserName(String userName);
    
}
