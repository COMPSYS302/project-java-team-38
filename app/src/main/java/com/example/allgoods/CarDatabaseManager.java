package com.example.allgoods;

import java.util.HashMap;


public class CarDatabaseManager {
    private static CarDatabaseManager instance; // Singleton instance
    private HashMap<CarListing,User> carManager;

    public static CarDatabaseManager getInstance(){
        if(instance  == null){
            instance = new CarDatabaseManager();
        }
        return instance;
    }

    public void addListing(User user,CarListing carListing){
        if(!carListing.getCar().getUser().equals(user)){
            throw new IllegalArgumentException("Invalid action");
        }

    }


}
