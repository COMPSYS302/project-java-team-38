package com.example.allgoods;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import android.util.Log;



public class CarDatabaseManager {
    private static CarDatabaseManager instance; // Singleton instance
    public HashMap<User,CarListing> carManager;

    private CarDatabaseManager() {
        carManager = new HashMap<>();
        Log.d("CarDatabaseManager", "Constructor called and carManager initialized.");
    }

    public static CarDatabaseManager getInstance(){
        if(instance  == null){
            instance = new CarDatabaseManager();
            Log.d("CarDatabaseManager", "getInstance(): New instance created.");
        }else{
            Log.d("CarDatabaseManager", "getInstance(): Existing instance returned.");
        }

        return instance;
    }

    public void addListing(User user,CarListing carListing){
        if (!Objects.equals(carListing.getCar().getUser(), user)) {
            throw new IllegalArgumentException("Invalid action");
        }
        carManager.put(user, carListing);
    }

    public HashMap<User,CarListing> getCarData() {
        return carManager;
    }


}
