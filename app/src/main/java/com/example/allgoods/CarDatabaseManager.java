package com.example.allgoods;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CarDatabaseManager {
    private static CarDatabaseManager instance; // Singleton instance
    private HashMap<User, List<CarListing>> carManager;

    private CarDatabaseManager() {
        carManager = new HashMap<>();
        Log.d("CarDatabaseManager", "Constructor called and carManager initialized.");
    }

    public static CarDatabaseManager getInstance() {
        if (instance == null) {
            instance = new CarDatabaseManager();
            Log.d("CarDatabaseManager", "getInstance(): New instance created.");
        } else {
            Log.d("CarDatabaseManager", "getInstance(): Existing instance returned.");
        }
        return instance;
    }

    // Adding a car listing for a user
    public void addListing(User user, CarListing carListing) {
        if (!Objects.equals(carListing.getCar().getOwner(), user)) {
            throw new IllegalArgumentException("CarListing does not belong to the given user");
        }
        // Get or create the list of car listings for this user
        List<CarListing> listings = carManager.getOrDefault(user, new ArrayList<>());
        listings.add(carListing);
        carManager.put(user, listings);
        Log.d("CarDatabaseManager", "addListing(): Listing added for user");
    }

    // Removing a car listing for a user
    public boolean removeListing(User user, CarListing carListing) {
        List<CarListing> listings = carManager.get(user);
        if (listings != null && listings.contains(carListing)) {
            boolean removed = listings.remove(carListing);
            if (listings.isEmpty()) {
                carManager.remove(user); // Optionally remove key if no listings left
            }
            Log.d("CarDatabaseManager", "removeListing(): Listing removed for user: " + user.getUsername());
            return removed;
        }
        return false;
    }

    // Retrieve all car listings for a user
    public List<CarListing> getListingsByUser(User user) {
        return carManager.getOrDefault(user, new ArrayList<>());
    }

    // Retrieve all car listings from all users
    public List<CarListing> getAllListings() {
        List<CarListing> allListings = new ArrayList<>();
        for (List<CarListing> listings : carManager.values()) {
            allListings.addAll(listings);
        }
        return allListings;
    }
}
