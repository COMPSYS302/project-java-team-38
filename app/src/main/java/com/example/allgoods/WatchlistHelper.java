package com.example.allgoods;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class WatchlistHelper {
    private static WatchlistHelper instance;
    private HashMap<User, List<CarListing>> watchlistDB;

    private WatchlistHelper() {
        this.watchlistDB = new HashMap<>();
    }

    public static synchronized WatchlistHelper getInstance() {
        if (instance == null) {
            instance = new WatchlistHelper();
        }
        return instance;
    }

    public void addToWatchlist(User user, CarListing carListing) {
        List<CarListing> watchlist = watchlistDB.get(user);
        if (watchlist == null) {
            watchlist = new ArrayList<>();
            watchlistDB.put(user, watchlist);
        }
        if (!watchlist.contains(carListing)) {
            watchlist.add(carListing);
            Log.d("WatchlistHelper", "Added to watchlist: " + carListing);
        }
        Log.d("WatchlistHelper", "Current watchlist size for user " + user + ": " + watchlist.size());
    }

    public void removeFromWatchlist(User user, CarListing carListing) {
        List<CarListing> watchlist = watchlistDB.get(user);
        if (watchlist != null && watchlist.contains(carListing)) {
            watchlist.remove(carListing);
            if (watchlist.isEmpty()) {
                watchlistDB.remove(user);
                Log.d("WatchlistHelper", "Watchlist removed for user: " + user);
            } else {
                watchlistDB.put(user, watchlist);
            }
            Log.d("WatchlistHelper", "Removed from watchlist: " + carListing);
        }
    }

    public List<CarListing> getWatchListUser(User user) {
        return watchlistDB.getOrDefault(user, new ArrayList<>());
    }
}