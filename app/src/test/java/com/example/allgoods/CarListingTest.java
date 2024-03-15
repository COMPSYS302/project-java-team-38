package com.example.allgoods;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class CarListingTest {

    @Test
    public void testValidCarListingCreation() {
        User owner = new User(1, "testUser", "Test@Password123");
        String[] tags = {"sedan", "automatic"};
        Car car = new Car(1, owner, "Toyota", "Camry", 2019, tags);
        Date listingDate = new Date();
        CarListing carListing = new CarListing(1, car, 25000.50, listingDate);

        assertEquals(1, carListing.getId());
        assertEquals(car, carListing.getCar());
        assertEquals(25000.50, carListing.getPrice(), 0.01);
        assertEquals(listingDate, carListing.getListingDate());
    }

    @Test
    public void testChangePrice() {
        User owner = new User(1, "testUser", "Test@Password123");
        String[] tags = {"sedan", "automatic"};
        Car car = new Car(1, owner, "Toyota", "Camry", 2019, tags);
        Date listingDate = new Date();
        CarListing carListing = new CarListing(1, car, 25000.50, listingDate);

        carListing.changePrice(30000.75);
        assertEquals(30000.75, carListing.getPrice(), 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChangePriceSamePrice() {
        User owner = new User(1, "testUser", "Test@Password123");
        String[] tags = {"sedan", "automatic"};
        Car car = new Car(1, owner, "Toyota", "Camry", 2019, tags);
        Date listingDate = new Date();
        CarListing carListing = new CarListing(1, car, 25000.50, listingDate);

        carListing.changePrice(25000.50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChangePriceInvalidNewPrice() {
        User owner = new User(1, "testUser", "Test@Password123");
        String[] tags = {"sedan", "automatic"};
        Car car = new Car(1, owner, "Toyota", "Camry", 2019, tags);
        Date listingDate = new Date();
        CarListing carListing = new CarListing(1, car, 25000.50, listingDate);

        carListing.changePrice(-5000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPriceZero() {
        User owner = new User(1, "testUser", "Test@Password123");
        String[] tags = {"sedan", "automatic"};
        Car car = new Car(1, owner, "Toyota", "Camry", 2019, tags);
        Date listingDate = new Date();
        new CarListing(1, car, 0, listingDate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPriceNegative() {
        User owner = new User(1, "testUser", "Test@Password123");
        String[] tags = {"sedan", "automatic"};
        Car car = new Car(1, owner, "Toyota", "Camry", 2019, tags);
        Date listingDate = new Date();
        new CarListing(1, car, -10000, listingDate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPriceTooHigh() {
        User owner = new User(1, "testUser", "Test@Password123");
        String[] tags = {"sedan", "automatic"};
        Car car = new Car(1, owner, "Toyota", "Camry", 2019, tags);
        Date listingDate = new Date();
        new CarListing(1, car, 200000000, listingDate);
    }
    @Test
    public void testCarListingTags() {
        User owner = new User(1, "testUser", "Test@Password123");
        String[] tags = {"sedan", "automatic", "gasoline", "black"};
        Car car = new Car(1, owner, "Toyota", "Camry", 2019, tags);
        Date listingDate = new Date();
        CarListing carListing = new CarListing(1, car, 25000.50, listingDate);

        String[] carTags = car.getCarTagsArray();
        assertEquals(4, carTags.length);
        assertArrayEquals(tags, car.getCarTagsArray());
    }

    @Test
    public void testChangeCarTags() {
        User owner = new User(1, "testUser", "Test@Password123");
        String[] tags = {"sedan", "automatic", "gasoline", "black"};
        Car car = new Car(1, owner, "Toyota", "Camry", 2019, tags);
        Date listingDate = new Date();
        CarListing carListing = new CarListing(1, car, 25000.50, listingDate);

        car.addTags("SUV");
        car.addTags("4WD");

        String[] newTags = {"sedan", "automatic", "gasoline", "black", "SUV", "4WD"};
        String[] updatedCarTags = car.getCarTagsArray();
        assertEquals(6, updatedCarTags.length);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCarTags() {
        User owner = new User(1, "testUser", "Test@Password123");
        // Attempt to add a tag with special characters
        String[] invalidTags = {"sedan", "automatic", "gasoline", "black!", "invalid#tag"};
        new Car(1, owner, "Toyota", "Camry", 2019, invalidTags);
    }
}
