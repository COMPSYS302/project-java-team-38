package com.example.allgoods;

// The User class represents a user in the system with their personal details and credentials.
public class User {
    private final String id; // Unique identifier for the user
    private String username; // Username of the user
    private String email; // Email address of the user
    private String encryptedPassword; // User's password in encrypted form for security
    // Utilizes the UserHelper class for validating user input and handling encryption
    UserHelper userHelper;

    // Constructor for creating a new User object with validation checks for email, username, and password
    public User(String id, String username, String password, String email) {
        this.userHelper = new UserHelper(); // Initialize UserHelper for input validation and encryption
        this.id = id; // Assign unique ID to user
        // Validate email format
        if (!userHelper.isValidEmail(email)) {
            throw new IllegalArgumentException("Please input a valid email address");
        } else {
            this.email = email;
        }
        // Validate username for special characters or spaces
        if (!userHelper.isValidUsername(username)) {
            throw new IllegalArgumentException("Please make sure your username does not have " +
                    "any special characters or spaces");
        } else {
            this.username = username;
        }
        // Validate password complexity requirements
        if (!userHelper.isValidPassword(password)) {
            throw new IllegalArgumentException("Be sure to include 8 letters which contain 2 " +
                    "numbers and a special character");
        } else {
            // Encrypts the password for secure storage
            this.encryptedPassword = userHelper.encrypt(password);
        }
    }

    // Getter method for retrieving the username
    public String getUsername() {
        return username;
    }

    // Getter method for retrieving the user ID
    public String getId() {
        return id;
    }

    // Getter method for retrieving the user's email address
    public String getEmail() {
        return email;
    }

    // Getter method for retrieving the decrypted password of the user
    public String getPassword() {
        return userHelper.decrypt(encryptedPassword);
    }

    // Setter method for updating the username after validation
    public void setUsername(String username) {
        try {
            // Validates and updates the username
            username = userHelper.changeUsername(username, this.username);
            this.username = username;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    // Method to update the user's password after validating the old and new passwords
    public void setPassword(String oldPassword, String newPassword, String newPassword2) {
        try {
            // Validates and updates the password, then encrypts the new password
            this.encryptedPassword = userHelper.changePassword(oldPassword, newPassword, newPassword2, encryptedPassword);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

}
