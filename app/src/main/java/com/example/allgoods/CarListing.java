package com.example.allgoods;
import java.util.Date;

public class CarListing {
    private final int id;
    private final Car car;
    private double price;
    private final Date listingDate;

    CarListingHelper carListingHelper;

    public CarListing(int id, Car car, double price, Date listingDate) {
        this.carListingHelper = new CarListingHelper();
        this.id = id;
        this.car = car;
        if(!carListingHelper.validatePrice(price)){
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

    //setter methods
    public void setPrice(double newPrice){
        price = newPrice;
    }

}
