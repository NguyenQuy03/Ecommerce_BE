package com.ecommerce.springbootecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.springbootecommerce.entity.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByUsernameAndStatus(String username, Boolean status);
    Optional<AccountEntity> findOneById(Long id);
    
    Optional<AccountEntity> findOneByEmail(String email);

    Optional<AccountEntity> findByUsername(String username);
}
