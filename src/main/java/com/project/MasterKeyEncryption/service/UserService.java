package com.project.MasterKeyEncryption.service;



import com.project.MasterKeyEncryption.model.User;
import com.project.MasterKeyEncryption.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final EncryptionService encryptionService;

    public UserService(UserRepository userRepository,
                       @Value("${app.master.key}") String masterKey) {
        this.userRepository = userRepository;
        this.encryptionService = new EncryptionService(masterKey);
    }

    public User createUser(String username, String sensitiveData) throws Exception {
        User user = new User();
        user.setUsername(username);
        user.setEncryptedData(encryptionService.encrypt(sensitiveData));
        return userRepository.save(user);
    }

    public String getUserData(String userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return encryptionService.decrypt(user.getEncryptedData());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(String id, String newData) throws Exception {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEncryptedData(encryptionService.encrypt(newData));
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}