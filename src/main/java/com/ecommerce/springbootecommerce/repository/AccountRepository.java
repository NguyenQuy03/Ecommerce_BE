package com.ecommerce.springbootecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.springbootecommerce.entity.AccountEntity;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByUsernameAndStatus(String username, Boolean status);
    Optional<AccountEntity> findById(Long id);
    
    Optional<AccountEntity> findByEmail(String email);

    @Query(value = "SELECT * FROM ACCOUNT WHERE BINARY username = :username", nativeQuery = true)
    Optional<AccountEntity> findByUsername(String username);
}
