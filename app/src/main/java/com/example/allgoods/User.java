package com.example.allgoods;
import androidx.annotation.Nullable;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;



import java.util.Base64;
import java.util.regex.Pattern;

public class User {
    private final String id;
    private String username;
    private String email;
    private String encryptedPassword;
    AuthenticationManager authenticationManager;
    private static final long BASE_TIMESTAMP = 1647657600000L; // Base timestamp (adjustable to your needs)
    private static final Random random = new Random();

    private static final String SECRET_KEY = "all_goods_key_of_secrets";

    public User(String username, String password, String email) {
        if(generateUniqueId().equals(null)){
            id = generateUniqueId2();
        }else{
            id = generateUniqueId();
        }
        if(!isValidEmail(email)){
            throw new IllegalArgumentException("Please input a valid email address");
        }
        else{
            this.email = email;
        }
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

    public String getId() {
        return id;
    }

    public String getEmail(){
        return email;
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

    private boolean isValidEmail(String email) {
        // Regular expression for email validation
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }
    @Nullable
    public static String generateUniqueId() {
        try {
            // Get the MAC address of the first network interface
            InetAddress localHost = InetAddress.getLocalHost();
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);
            byte[] macBytes = networkInterface.getHardwareAddress();

            // Convert MAC address bytes to hex string
            StringBuilder macStringBuilder = new StringBuilder();
            for (byte b : macBytes) {
                macStringBuilder.append(String.format("%02X", b));
            }

            return macStringBuilder.toString();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace(); // Handle exceptions gracefully
            return null; // Return null if unable to get MAC address
        }
    }

    public static String generateUniqueId2() {
        long currentTimestamp = System.currentTimeMillis(); // Current timestamp
        long uniquePart = currentTimestamp - BASE_TIMESTAMP; // Subtracting base timestamp

        // Adding a random portion to ensure uniqueness
        uniquePart = uniquePart * 1000 + random.nextInt(1000);

        return String.valueOf(uniquePart); // Convert to string
    }

}
