package com.example.allgoods;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;

public class User {
    private final int id;
    private String username;
    private String encryptedPassword;

    private static final String SECRET_KEY = "all_goods_key_of_secrets";

    public User(int id, String username, String password) {
        this.id = id;
        if (!isValidUsername(username)) {
            throw new IllegalArgumentException("Please make sure your username does not have " +
                    "any special characters or spaces");
        } else {
            this.username = username;
        }
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Be sure to include 8 letters which contain 2 " +
                    "numbers and a special character");
        } else {
            this.encryptedPassword = encrypt(password);
        }
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return decrypt(encryptedPassword);
    }

    public void changeUsername(String newUsername, String username) {
        if (!isValidUsername(newUsername)) {
            throw new IllegalArgumentException("Please make sure your username does not have " +
                    "any special characters or spaces");
        } else {
            this.username = newUsername;
        }
    }

    public void changePassword(String oldPassword, String newPassword, String newPassword2) {
        String oldPasswordValidation = decrypt(encryptedPassword);
        if (!oldPasswordValidation.equals(oldPassword)) {
            throw new IllegalArgumentException("Old Password is incorrect");
        }
        if (!newPassword.equals(newPassword2)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        if (!isValidPassword(newPassword)) {
            throw new IllegalArgumentException("New Password does not meet the criteria");
        }

        this.encryptedPassword = encrypt(newPassword);
    }

    private String encrypt(String decryptedPassword) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(decryptedPassword.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new IllegalStateException("Error encrypting password", e);
        }
    }

    private String decrypt(String encryptedPassword) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new IllegalStateException("Error decrypting password", e);
        }
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9].*[0-9])(?=.*[!@#$%^&*()-+=]).{8,}$";
        return password.matches(regex);
    }

    private boolean isValidUsername(String username) {
        String regex = "^[a-zA-Z0-9]{6,}$";
        return username.matches(regex);
    }
}
