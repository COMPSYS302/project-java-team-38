package com.example.allgoods;
import android.net.Uri;

import android.net.Uri;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;

public class CarListing {
    private final String id;
    private final Car car;
    private Integer price;
    private final ZonedDateTime listingDate;

    CarListingHelper carListingHelper;

    ArrayList<Uri> images = new ArrayList<>();

    public CarListing(String id, Car car, Integer price, ZonedDateTime listingDate, ArrayList<Uri> uriImages) {
        this.carListingHelper = new CarListingHelper();
        this.id = id;
        this.car = car;
        if(!carListingHelper.validatePrice(price)){
            throw new IllegalArgumentException("Price must be over $0 and below $150,000,000" +
                    "and must not include any letters or special characters");
        }
        this.price = price;
        this.listingDate = listingDate;
        images.addAll(uriImages);
    }

    // Getter methods
    public String getId() {
        return id;
    }

    public Car getCar() {
        return car;
    }

    public double getPrice() {
        return price;
    }

    public ZonedDateTime getListingDate() {
        return listingDate;
    }


    public void setPrice(Integer newPrice) {
        if (carListingHelper.validatePrice(newPrice)) {
            price = newPrice;
        }
    }

    public Uri getFirstImage(){
        return images.get(0);
    }


}
