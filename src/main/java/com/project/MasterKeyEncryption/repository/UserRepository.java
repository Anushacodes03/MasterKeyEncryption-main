package com.project.MasterKeyEncryption.repository;

// src/main/java/com/yourapp/repository/UserRepository.java

import com.project.MasterKeyEncryption.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}
