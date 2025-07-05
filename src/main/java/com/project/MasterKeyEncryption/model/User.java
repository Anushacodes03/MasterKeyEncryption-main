package com.project.MasterKeyEncryption.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String encryptedData; // This will store the encrypted user data
    private String iv; // Initialization Vector for AES encryption
}
