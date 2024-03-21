package com.example.allgoods;

import java.util.Base64;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class UserHelper {

    private static final String SECRET_KEY = "all_goods_key_of_secrets";

    public String changeUsername(String newUsername, String username) {
        if (!isValidUsername(newUsername)) {
            throw new IllegalArgumentException("Please make sure your username does not have " +
                    "any special characters or spaces");
        }
        return newUsername;
    }
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

        return encrypt(newPassword);
    }

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

    public boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9].*[0-9])(?=.*[!@#$%^&*()-+=]).{8,}$";
        return password.matches(regex);
    }

    public boolean isValidUsername(String username) {
        String regex = "^[a-zA-Z0-9]{6,}$";
        return username.matches(regex);
    }

    public boolean isValidEmail(String email) {
        // Regular expression for email validation
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }

}
