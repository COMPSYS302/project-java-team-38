package com.example.allgoods;
import java.util.Date;

public class CarListing {
    private final int id;
    private final Car car;
    private double price;
    private final Date listingDate;

    public CarListing(int id, Car car, double price, Date listingDate) {
        this.id = id;
        this.car = car;
        if(!validatePrice(price)){
            throw new IllegalArgumentException("Price must be over $0 and below $150,000,000" +
                    "and must not include any letters or special characters");
        }
        this.price = price;
        this.listingDate = listingDate;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public Car getCar() {
        return car;
    }

    public double getPrice() {
        return price;
    }

    public Date getListingDate() {
        return listingDate;
    }

    // changePrice() function ensures that if user attempts to change price of listing
    // it is in the correct ranges and does not include any special characters or letters
    public void changePrice(double newPrice){
        if(newPrice == price){
            throw new IllegalArgumentException("Price must change to a different price");
        }
        else if(!validatePrice(newPrice)){
            throw new IllegalArgumentException("price cannot be below $0 or above @150,000,000" +
                    "and must not have any letters or special characters");
        }
        else {
            price = newPrice;
        }
    }

    // validatePrice() function validates wheter the price is in the correct range and is correct
    private boolean validatePrice(double price) {
        // Check if the price is less than 150 million and contains only digits
        if (price >= 0 && price < 150_000_000) {
            String priceString = Double.toString(price);
            return priceString.matches("\\d*\\.?\\d+");
        }
        return false;
    }
}
