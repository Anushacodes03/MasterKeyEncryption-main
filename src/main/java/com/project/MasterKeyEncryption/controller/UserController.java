package com.project.MasterKeyEncryption.controller;

// src/main/java/com/yourapp/controller/UserController.java
import com.project.MasterKeyEncryption.model.User;
import com.project.MasterKeyEncryption.service.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Allow all origins for development
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequest request) {
        try {
            User user = userService.createUser(request.getUsername(), request.getSensitiveData());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Failed to create user: " + e.getMessage())
            );
        }
    }

    // READ (Single)
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id) {
        try {
            String decryptedData = userService.getUserData(id);
            return ResponseEntity.ok(Map.of("data", decryptedData));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(
                    Map.of("error", "User not found: " + e.getMessage())
            );
        }
    }

    // READ (All)
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable String id,
            @RequestBody UserRequest request) {
        try {
            User updatedUser = userService.updateUser(id, request.getSensitiveData());
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Update failed: " + e.getMessage())
            );
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Deletion failed: " + e.getMessage())
            );
        }
    }

    // DTO for request body
    @Data
    static class UserRequest {
        private String username;
        private String sensitiveData;
    }
}