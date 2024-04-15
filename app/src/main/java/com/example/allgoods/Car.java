package com.example.allgoods;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

public class Car {
    private String carId;
    private User owner;
    private String make;
    private String model;
    private int year;
    private int odo;

    private String type;

    // for generating unique id for each car
    private static final long BASE_TIMESTAMP = 1647657600000L; // Base timestamp (adjustable to your needs)
    private static final Random random = new Random();

    public Car(User owner, String make, String model, int year, int odo, String type){
        this.carId = generateUniqueId();
        this.owner = owner;
        this.make = make;
        this.model = model;
        this.year = year;
        this.odo = odo;
        this.type = type;
    } 

    // Getter methods
    public String getCarId() {
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
    public User getUser(){
        return owner;
    }
    public int getOdo(){
        return odo;
    }

    public String getType(){
        return this.type;
    }

    public static String generateUniqueId() {
        long currentTimestamp = System.currentTimeMillis(); // Current timestamp
        long uniquePart = currentTimestamp - BASE_TIMESTAMP; // Subtracting base timestamp

        // Adding a random portion to ensure uniqueness
        uniquePart = uniquePart * 1000 + random.nextInt(1000);

        return String.valueOf(uniquePart); // Convert to string
    }


}
