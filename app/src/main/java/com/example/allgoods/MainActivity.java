package com.example.allgoods;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    private Button backButton, homeButton, plusButton, watchlistButton;
    private ImageView ivNavigationButton;

    RecyclerView rvCarListings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         rvCarListings = findViewById(R.id.rvCarListings);

        // Initialize demo data
        DemoDataInitializer demoDataInitializer = new DemoDataInitializer();
        demoDataInitializer.initializeDemoData();
        CarDatabaseManager dbManager = CarDatabaseManager.getInstance();


        // Assuming CarDatabaseManager has a method to get all listings
        // Convert Set to List if necessary
        List<CarListing> carListings = new ArrayList<>(dbManager.getCarData().values());

        // Set up the RecyclerView with the adapter
        CarAdapter carAdapter = new CarAdapter(this, new ArrayList<>(carListings)); // Assuming carListings is correctly initialized
        rvCarListings.setAdapter(carAdapter); // Corrected method call
        rvCarListings.setLayoutManager(new LinearLayoutManager(this)); // Ensure LinearLayoutManager is imported


        // Initialize buttons
        //backButton = findViewById(R.id.backButton);

        homeButton = findViewById(R.id.homeButton);
        plusButton = findViewById(R.id.plusButton);
        watchlistButton = findViewById(R.id.watchlistButton);

        // Set click listeners for each button
        /*backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simulate back navigation
                finish(); // Close current activity
            }
        });*/
        ivNavigationButton = findViewById(R.id.ivProfileImage);
        ivNavigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class); // Replace with your target Activity class
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            }
        });


        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Home
                Toast.makeText(MainActivity.this, "Home clicked", Toast.LENGTH_SHORT).show();
                // Intent to navigate to Home Activity could go here
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Actions or Intent to add something could go here
                Intent createListingStart = new Intent(MainActivity.this, CreateListingActivity.class);
                startActivity(createListingStart);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            }
        });

        watchlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Watchlist
                Toast.makeText(MainActivity.this, "Watchlist clicked", Toast.LENGTH_SHORT).show();
                // Intent to navigate to Watchlist Activity could go here
            }
        });
    }
}
