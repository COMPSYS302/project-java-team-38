package com.example.allgoods;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Car {
    private int carId;
    private User owner;
    private String make;
    private String model;
    private int year;
    private Set<String> carTags = new HashSet<>();

    public Car(int carId, User owner, String make, String model, int year, String[] carTags){
        this.carId = carId;
        this.owner = owner;
        this.make = make;
        this.model = model;
        this.year = year;
        Collections.addAll(this.carTags, carTags);
    }

    // Getter methods
    public int getCarId() {
        return carId;
    }

    public User getOwner() {
        return owner;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String[] getCarTagsArray() {
        return carTags.toArray(new String[0]);
    }

    public void addTags(String tags){
        Collections.addAll(this.carTags, tags);
    }

}
