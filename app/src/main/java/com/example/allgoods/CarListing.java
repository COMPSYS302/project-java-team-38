package com.example.allgoods;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class CarListing implements Parcelable {
    private final String id;
    private final Car car;  // Car must also implement Parcelable
    private Integer price;
    private final ZonedDateTime listingDate;
    private final String carDescription;
    private ArrayList<Uri> images;

    CarListingHelper carListingHelper;

    public CarListing(String id, Car car, Integer price, ZonedDateTime listingDate, ArrayList<Uri> images, String carDescription) {
        this.carListingHelper = new CarListingHelper();
        this.id = id;
        this.car = car;
        if (!carListingHelper.validatePrice(price)) {
            throw new IllegalArgumentException("Price must be over $0 and below $150,000,000 and must not include any letters or special characters");
        }
        this.price = price;
        this.listingDate = listingDate;
        this.carDescription = carDescription;
        this.images = new ArrayList<>(images);
    }

    protected CarListing(Parcel in) {
        id = in.readString();
        car = in.readParcelable(Car.class.getClassLoader());
        price = in.readInt();
        listingDate = (ZonedDateTime) in.readSerializable();
        carDescription = in.readString();
        images = in.createTypedArrayList(Uri.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(car, flags);
        dest.writeInt(price);
        dest.writeSerializable(listingDate);
        dest.writeString(carDescription);
        dest.writeTypedList(images);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CarListing> CREATOR = new Creator<CarListing>() {
        @Override
        public CarListing createFromParcel(Parcel in) {
            return new CarListing(in);
        }

        @Override
        public CarListing[] newArray(int size) {
            return new CarListing[size];
        }
    };

    // Getter methods
    public String getId() {
        return id;
    }

    public Car getCar() {
        return car;
    }

    public ArrayList<Uri> getImages(){
        return images;
    }

    public Integer getPrice() {
        return price;
    }

    public String getCarDescription() {
        return carDescription;
    }

    public ZonedDateTime getListingDate() {
        return listingDate;
    }

    public Uri getFirstImage() {
        if (!images.isEmpty()) {
            return images.get(0);
        }
        return null;
    }

    public void setPrice(Integer newPrice) {
        if (carListingHelper.validatePrice(newPrice)) {
            this.price = newPrice;
        }
    }
}
