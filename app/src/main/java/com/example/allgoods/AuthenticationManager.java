package com.example.allgoods;

import java.util.ArrayList;
import java.util.HashMap;

public class AuthenticationManager {

    HashMap<String,User> users;

    //Password authenticator
    //adds email and password into hashmap to extract
    // username is the key so it must be unique
    public AuthenticationManager() {
        users = new HashMap<>();
    }

    public void addUserCredentials(User user){
        if(users.containsKey(user.getEmail())){
            throw new IllegalArgumentException("You have already registered. Please Login!");
        }
        else {
            users.put(user.getEmail(), user);
        }

    }

    // loginAuthenticator() function validates whether the user credintials are correct
    // inputs the user email and outputs a boolean value
    public boolean loginAuthenticator(String email, String password){
        if(!users.containsKey(email)){
            throw new IllegalArgumentException("Email is not registered. Please register!");
        }
        // this if statement checks wether the users password matches the password
        if(!users.get(email).getPassword().equals(password)){
            throw new IllegalArgumentException("Password does not match Please try again");
        }
        return true;
    }

}
