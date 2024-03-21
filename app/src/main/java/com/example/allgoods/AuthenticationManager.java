package com.example.allgoods;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.HashMap;

public class AuthenticationManager {

    private static AuthenticationManager instance; // Singleton instance
    private HashMap<String, User> users; // HashMap to store user credentials

    // Private constructor to prevent direct instantiation
    private AuthenticationManager() {
        users = new HashMap<>();
    }

    // Method to get the Singleton instance of AuthenticationManager
    public static AuthenticationManager getInstance() {
        if (instance == null) {
            instance = new AuthenticationManager();
        }
        return instance;
    }

    // Method to add user credentials to the HashMap
    public void addUserCredentials(User user) {
        // Check if the email already exists in the HashMap
        if (users.containsKey(user.getEmail())) {
            throw new IllegalArgumentException("You have already registered. Please Login!");
        } else {
            // Add user credentials to the HashMap
            users.put(user.getEmail(), user);
        }
    }

    // Method to authenticate user login
    public boolean loginAuthenticator(String email, String password) {
        // Check if the email exists in the HashMap
        if (!users.containsKey(email) || databaseValidator() == false) {
            throw new IllegalArgumentException("Email is not registered. Please register!");
        }
        // Check if the password matches the stored password for the email
        if (!users.get(email).getPassword().equals(password)) {
            throw new IllegalArgumentException("Password does not match. Please try again");
        }
        return true; // Authentication successful
    }

    public boolean databaseValidator(){
        if(users.size() == 0){
            return false;
        }

        return true;
    }
}

