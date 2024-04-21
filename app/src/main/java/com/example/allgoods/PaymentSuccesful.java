package com.example.allgoods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentSuccesful extends AppCompatActivity {
    private ImageButton backButton;
    private TextView tvYear, tvPrice, tvMileage, tvNumberPlate, tvModel, carDescription;
    private ImageButton homeButton, plusButton, watchlistButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_confirmation);
        backButton = findViewById(R.id.btnBack);
        backButton.setOnClickListener(v -> navigateBack());

        // Initialize TextViews
        tvYear = findViewById(R.id.YearofCar);
        tvPrice = findViewById(R.id.priceCar);
        tvMileage = findViewById(R.id.MileageofCar);
        tvNumberPlate = findViewById(R.id.NumberPlateofCar);
        tvModel = findViewById(R.id.ModelType);
        carDescription = findViewById(R.id.tvCarDescription);
        homeButton = findViewById(R.id.homeButton);
        plusButton = findViewById(R.id.plusButton);
        watchlistButton = findViewById(R.id.watchlistButton);

        // Get car listing from intent
        CarListing carListing = getIntent().getParcelableExtra("CarListing");
        if (carListing != null) {
            displayCarDetails(carListing);
        }
        homeButton.setOnClickListener(v -> navigateToMainPage());
        plusButton.setOnClickListener(v -> navigateToCreateListing());
        watchlistButton.setOnClickListener(v -> navigateToWatchList());
    }

    private void navigateToMainPage() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }

    private void navigateToCreateListing() {
        startActivity(new Intent(this, CreateListingActivity.class));
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    private void navigateToWatchList() {
        startActivity(new Intent(this, WatchListActivity.class));
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    private void navigateBack() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void displayCarDetails(CarListing carListing) {
        carDescription.setText(String.format("%s",carListing.getCarDescription()));
        tvYear.setText(String.format("Year: %d", carListing.getCar().getYear()));
        tvPrice.setText(String.format("Price: $%d", carListing.getPrice()));
        tvMileage.setText(String.format("Mileage: %d km", carListing.getCar().getOdo()));
        tvNumberPlate.setText(String.format("Number Plate: %s", carListing.getCar().getNumberPlate()));
        tvModel.setText(String.format("Model: %s %s", carListing.getCar().getMake(), carListing.getCar().getModel()));
    }
}
