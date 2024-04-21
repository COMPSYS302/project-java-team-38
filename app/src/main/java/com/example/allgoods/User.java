package com.example.allgoods;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

public class User implements Parcelable {
    private final String id;
    private String username;
    private String email;
    private String encryptedPassword;
    private List<CarListing> recentViewedCarListings;  // List to store recently viewed car listings

    transient UserHelper userHelper = new UserHelper();  // Helper class for user operations, not part of the parcel

    public User(String id, String username, String password, String email) {
        this.id = id;
        if (userHelper.isValidUsername(username)) {
            this.username = username;
        }else{
            throw new IllegalArgumentException("Please ensure there are no special characters in your username");
        }
        if(userHelper.isValidEmail(email)){
            this.email = email;
        }
        else{
            throw new IllegalArgumentException("Please ensure your email is valid");
        }
        if(userHelper.isValidPassword(password)) {
            this.encryptedPassword = userHelper.encrypt(password);
        }else{
            throw new IllegalArgumentException("Please Ensure your password has a uppercase and a special character");
        }
        this.recentViewedCarListings = new ArrayList<>(3);
    }

    // Constructor used for parceling
    protected User(Parcel in) {
        id = in.readString();
        username = in.readString();
        email = in.readString();
        encryptedPassword = in.readString();
        recentViewedCarListings = new ArrayList<>();
        in.readTypedList(recentViewedCarListings, CarListing.CREATOR);  // Read the list of CarListing objects
        userHelper = new UserHelper();  // Recreate the transient helper
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(encryptedPassword);
        dest.writeTypedList(recentViewedCarListings);  // Write the list of CarListing objects
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    // Getters
    public String getUsername() { return username; }
    public String getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return userHelper.decrypt(encryptedPassword); }
    public List<CarListing> getRecentViewedCarListings() { return recentViewedCarListings; }


    public void addViewedCarListing(CarListing carListing) {
        if (recentViewedCarListings.size() == 3) {
            recentViewedCarListings.remove(0); // Remove the oldest viewed listing if the list is full
        }
        recentViewedCarListings.add(carListing); // Add the new listing
    }

    public void setUsername(String username) {
        if (userHelper.isValidUsername(username)) {
            this.username = username;
        } else {
            throw new IllegalArgumentException("Invalid username");
        }
    }

    public void setPassword(String oldPassword, String newPassword, String newPassword2) {
        if (userHelper.changePassword(oldPassword, newPassword, newPassword2, encryptedPassword) != null) {
            this.encryptedPassword = userHelper.encrypt(newPassword);
        } else {
            throw new IllegalArgumentException("Password update failed");
        }
    }
}
