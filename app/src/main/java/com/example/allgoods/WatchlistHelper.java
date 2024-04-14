package com.example.allgoods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WatchlistHelper {
    // Private static instance of the class
    private static WatchlistHelper instance;

    private HashMap<User, List<CarListing>> watchlistDB;

    // Private constructor to prevent instantiation from other classes
    private WatchlistHelper() {
        this.watchlistDB = new HashMap<>();
    }

    // Public static method to get the instance of the class
    public static WatchlistHelper getInstance() {
        // Create the instance if it doesn't already exist
        if (instance == null) {
            instance = new WatchlistHelper();
        }
        return instance;
    }

    // Example of a method in the singleton class
    public void addToWatchlist(User user ,CarListing carListing) {
        // Retrieve the watchlist for the specified user, or create a new list if one does not exist
        List<CarListing> watchlist = watchlistDB.getOrDefault(user, new ArrayList<>());
        // Add the car listing if it's not already in the watchlist
        if (!watchlist.contains(carListing)) {
            watchlist.add(carListing);
            watchlistDB.put(user, watchlist); // Put the updated list back into the map
        }
    }

    // Example of another method
    public void removeFromWatchlist(User user, CarListing carListing) {
        // Retrieve the watchlist for the specified user
        List<CarListing> watchlist = watchlistDB.get(user);
        if (watchlist != null) {
            // Remove the car listing if it exists in the watchlist
            watchlist.remove(carListing);
            // If the watchlist is empty after removal, consider removing the key from the map entirely
            if (watchlist.isEmpty()) {
                watchlistDB.remove(user);
            } else {
                watchlistDB.put(user, watchlist); // Update the map with the modified list
            }
        }
    }

    public List<CarListing> getWatchListUser(User user){
        return watchlistDB.getOrDefault(user, new ArrayList<>());
    }
}
