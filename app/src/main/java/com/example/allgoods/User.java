package com.example.allgoods;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private final int id;
    private String username;
    private String encryptedPassword;

    // encryption key for passwords
    private static final String SECRET_KEY = "all_goods_key_of_secrets";

    public User(int id, String username, String password){
        this.id = id;
        if(!isValidUsername(username)){
            throw new RuntimeException("Please make sure your username does not have" +
                    "any special characters or spaces");
        }
        else {
            this.username = username;
        }
        if(!isValidPassword(password)){
            throw new RuntimeException("Be sure to include 8 letters which contain 2" +
                    "numbers and a special character");
        }
        else {
            encryptedPassword = encrypt(password);
        }
    }

    // getUsername() function gets the username
    public String getUsername() {
        return username;
    }

    // getId() function gets the id
    public int getId() {
        return id;
    }

    // getPassword() function decrypts the password and gets password
    public String getPassword() {
        return decrypt(encryptedPassword);
    }

    // Algorithm which encrypts passwords
    // encrypt() function takes plain string format and uses AES encryption with
    // the use of a secret key
    public String encrypt(String decryptedPassword) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(decryptedPassword.getBytes());
            return new String(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
    // Algorithm which decrypts passwords
    // decrypt() function takes the encrypted password and decrypts is using the
    // same key
    public String decrypt(String encryptedPassword){
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(encryptedPassword.getBytes());
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // changeUsername() function changes the username of the user
    // inputs userid and the new username and changes the username of the user
    public void changeUsername(int userId, String newUsername){
        if(!isValidUsername(newUsername)){
            throw new RuntimeException("Please make sure your username does not have" +
                    "any special characters or spaces");
        }
        else {
            this.username = newUsername;
        }
    }

    // changePassword() function changes the password of the user
    // function inputs the oldPassword, newPassword and newPassword2
    // validates if the oldPassword matches the encryptedPassword.
    // if it does then validates if the newPassword and newPassword2
    // if this is validated then it changes the password

    public void changePassword(String oldPassword, String newPassword, String newPassword2){
        String oldPasswordValidation = decrypt(encryptedPassword);
        if(!oldPasswordValidation.equals(oldPassword)){
            throw new RuntimeException("Old Password is incorrect");
        }
        if(!newPassword.equals(newPassword2)){
            throw new RuntimeException("Passwords do not match");
        }
        if (!isValidPassword(newPassword)) {
            throw new RuntimeException("New Password does not meet the criteria");
        }

        this.encryptedPassword = encrypt(newPassword);
    }

    // isValidPassword() function checks whether the
    // Password must be at least 8 characters long and contain
    // 2 numbers and 1 special character
    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9].*[0-9])(?=.*[!@#$%^&*()-+=]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    // isValidUsername() function checks whether the username is longer than 5 character
    // does not include any spaces or special characters
    private boolean isValidUsername(String username) {
        String regex = "^[a-zA-Z0-9]{6,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
}
