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

    


}
