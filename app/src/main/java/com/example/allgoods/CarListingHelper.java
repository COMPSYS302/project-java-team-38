package com.example.allgoods;

public class CarListingHelper {

    // changePrice() function ensures that if user attempts to change price of listing
    // it is in the correct ranges and does not include any special characters or letters
    public void changePrice(double newPrice, double oldPrice, CarListing carListing){
        if(newPrice == oldPrice){
            throw new IllegalArgumentException("Price must change to a different price");
        }
        else if(!validatePrice(newPrice)){
            throw new IllegalArgumentException("price cannot be below $0 or above @150,000,000" +
                    "and must not have any letters or special characters");
        }
        carListing.setPrice(newPrice);
    }

    // validatePrice() function validates wheter the price is in the correct range and is correct
    public boolean validatePrice(double price) {
        // Check if the price is less than 150 million and contains only digits
        if (price >= 0 && price < 150_000_000) {
            String priceString = Double.toString(price);
            return priceString.matches("\\d*\\.?\\d+");
        }
        return false;
    }

}
