package com.example.allgoods;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class CarListingTest {

   /* @Test
    public void testValidCarListingCreation() {
        User owner = new User( "testUser", "Test@Password123", "testuser@example.com");
        String[] tags = {"sedan", "automatic"};
        Car car = new Car( owner, "Toyota", "Camry", 2019, tags,38392);
        Date listingDate = new Date();
        CarListing carListing = new CarListing( car, 25000.50, listingDate);

        assertEquals(1, carListing.getId());
        assertEquals(car, carListing.getCar());
        assertEquals(25000.50, carListing.getPrice(), 0.01);
        assertEquals(listingDate, carListing.getListingDate());
    }

    @Test
    public void testChangePrice() {
        User owner = new User(1, "testUser", "Test@Password123", "testuser@example.com");
        String[] tags = {"sedan", "automatic"};
        Car car = new Car(1, owner, "Toyota", "Camry", 2019, tags,303820);
        Date listingDate = new Date();
        CarListing carListing = new CarListing(1, car, 25000.50, listingDate);

        carListing.changePrice(30000.75);
        assertEquals(30000.75, carListing.getPrice(), 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChangePriceSamePrice() {
        User owner = new User(1, "testUser", "Test@Password123", "testuser@example.com");
        String[] tags = {"sedan", "automatic"};
        Car car = new Car(1, owner, "Toyota", "Camry", 2019, tags,38392);
        Date listingDate = new Date();
        CarListing carListing = new CarListing(1, car, 25000.50, listingDate);

        carListing.changePrice(25000.50);
    }

    @Test
    public void testGetOwnerEmail() {
        User owner = new User(1, "testUser", "Test@Password123", "testuser@example.com");
        String[] tags = {"sedan", "automatic"};
        Car car = new Car(1, owner, "Toyota", "Camry", 2019, tags,309382);
        Date listingDate = new Date();
        CarListing carListing = new CarListing(1, car, 25000.50, listingDate);

        assertEquals("testuser@example.com", carListing.getCar().getOwner().getEmail());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEmail() {
        User owner = new User(1, "testUser", "Test@Password123", "invalidemail.com");
        String[] tags = {"sedan", "automatic"};
        Car car = new Car(1, owner, "Toyota", "Camry", 2019, tags,3739);
        Date listingDate = new Date();
        new CarListing(1, car, 25000.50, listingDate);
    }*/
}
