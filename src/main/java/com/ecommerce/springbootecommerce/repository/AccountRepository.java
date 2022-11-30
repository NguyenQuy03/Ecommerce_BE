package com.ecommerce.springbootecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.springbootecommerce.entity.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    AccountEntity findOneByUserNameAndStatus(String username, Boolean status);
    AccountEntity findOneByUserName(String username);
    AccountEntity findOneById(Long id);
    Optional<AccountEntity> findByEmail(String email);
    
    @Query(value = "SELECT username FROM account;", nativeQuery = true)
    List<String> findAllUserName();
    
    @Query(value = "SELECT email FROM account;", nativeQuery = true)
    List<String> findAllEmail();
}
