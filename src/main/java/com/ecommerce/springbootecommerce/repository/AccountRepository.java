package com.ecommerce.springbootecommerce.repository;

import com.ecommerce.springbootecommerce.entity.AccountEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<AccountEntity, String> {
    Optional<AccountEntity> findByUsernameAndStatus(String username, Boolean status);
    Optional<AccountEntity> findOneById(String id);
    
    Optional<AccountEntity> findOneByEmail(String email);

    Optional<AccountEntity> findByUsername(String username);
}
