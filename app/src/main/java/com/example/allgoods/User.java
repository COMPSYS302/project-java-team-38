package com.example.allgoods;



import java.util.Random;


public class User {
    private final String id;
    private String username;
    private String email;
    private String encryptedPassword;
    AuthenticationManager authenticationManager;
    private static final long BASE_TIMESTAMP = 1647657600000L; // Base timestamp (adjustable to your needs)
    private static final Random random = new Random();
    //bring user helper class
    UserHelper userHelper;

    public User(String id,String username, String password, String email) {
        this.id = id;
        if(!userHelper.isValidEmail(email)){
            throw new IllegalArgumentException("Please input a valid email address");
        }
        else{
            this.email = email;
        }
        if (userHelper.isValidUsername(username)) {
            throw new IllegalArgumentException("Please make sure your username does not have " +
                    "any special characters or spaces");
        } else {
            this.username = username;
        }
        if ( userHelper.isValidPassword(password)) {
            throw new IllegalArgumentException("Be sure to include 8 letters which contain 2 " +
                    "numbers and a special character");
        } else {
            this.encryptedPassword =userHelper.encrypt(password);
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
        return  userHelper.decrypt(encryptedPassword);
    }

}
