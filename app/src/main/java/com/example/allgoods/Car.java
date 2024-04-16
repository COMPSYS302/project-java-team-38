package com.example.allgoods;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Random;

public class Car implements Parcelable {
    private String carId;
    private User owner;  // Assume User class also implements Parcelable
    private String make;
    private String model;
    private int year;
    private int odo;
    private String type;

    private String numberPlate;

    // for generating unique id for each car
    private static final long BASE_TIMESTAMP = 1647657600000L; // Base timestamp (adjustable to your needs)
    private static final Random random = new Random();

    public Car(User owner, String make, String model, int year, int odo, String type, String numberPlate) {
        this.carId = generateUniqueId();
        this.owner = owner;
        this.make = make;
        this.model = model;
        this.year = year;
        this.odo = odo;
        this.type = type;
        this.numberPlate = numberPlate;
    }

    protected Car(Parcel in) {
        carId = in.readString();
        owner = in.readParcelable(User.class.getClassLoader());
        make = in.readString();
        model = in.readString();
        year = in.readInt();
        odo = in.readInt();
        type = in.readString();
        numberPlate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(carId);
        dest.writeParcelable(owner, flags);
        dest.writeString(make);
        dest.writeString(model);
        dest.writeInt(year);
        dest.writeInt(odo);
        dest.writeString(type);
        dest.writeString(numberPlate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    // Getter methods
    public String getCarId() {
        return carId;
    }

    public String getNumberPlate(){
        return numberPlate;
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

    public int getOdo() {
        return odo;
    }

    public String getType() {
        return type;
    }

    public static String generateUniqueId() {
        long currentTimestamp = System.currentTimeMillis();
        long uniquePart = currentTimestamp - BASE_TIMESTAMP;
        uniquePart = uniquePart * 1000 + random.nextInt(1000);
        return String.valueOf(uniquePart);
    }
}
