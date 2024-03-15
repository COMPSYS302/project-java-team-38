package com.example.allgoods;

public class Watchlist {
    private int listingId;
    private User user;
    private Car car;

    public Watchlist(int id, User user, Car car) {
        this.listingId = id;
        this.user = user;
        this.car = car;
    }

    public int getId() {
        return listingId;
    }

    public User getUser() {
        return user;
    }

    public Car getCar() {
        return car;
    }
}
