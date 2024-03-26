package com.example.allgoods;

import java.util.Base64;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

// The UserHelper class provides utility methods for user management tasks such as
// validation of usernames, passwords, and emails, as well as encryption and decryption of passwords.
public class UserHelper {

    // Secret key for AES encryption/decryption. This should be kept secure and not hard-coded in a real application.
    private static final String SECRET_KEY = "all_goods_key_of_secrets";

    // Method to change the username with validation to ensure it doesn't contain special characters or spaces
    // and is not the same as the old username.
    public String changeUsername(String newUsername, String username) {
        if (!isValidUsername(newUsername)) {
            throw new IllegalArgumentException("Please make sure your username does not have " +
                    "any special characters or spaces");
        }
        if(newUsername.equals(username)){
            throw new IllegalArgumentException("Username that is being changed is the same");
        }
        return newUsername;
    }

    // Method to change the password after validating the old password, ensuring the new passwords match,
    // and checking the new password against a set criteria.
    public String changePassword(String oldPassword, String newPassword, String newPassword2,String encryptedPassword) {
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
        if(oldPasswordValidation.equals(newPassword)){
            throw new IllegalArgumentException("Cannot change the password to your current password");
        }

        return encrypt(newPassword);
    }

    // Method to encrypt a given password using AES encryption. Throws IllegalStateException on error.
    public String encrypt(String decryptedPassword) {
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

    // Method to decrypt an encrypted password using AES. Throws IllegalStateException on error.
    public String decrypt(String encryptedPassword) {
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

    // Validates a password against specific criteria: minimum 8 characters, at least 2 digits, and 1 special character.
    public boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9].*[0-9])(?=.*[!@#$%^&*()-+=]).{8,}$";
        return password.matches(regex);
    }

    // Validates a username to ensure it is at least 6 characters long and contains only letters and digits.
    public boolean isValidUsername(String username) {
        String regex = "^[a-zA-Z0-9]{6,}$";
        return username.matches(regex);
    }

    // Validates an email address against a standard email format.
    public boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }
}
