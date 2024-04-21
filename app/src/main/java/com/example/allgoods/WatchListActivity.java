package com.example.allgoods;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WatchListActivity extends AppCompatActivity {

    private RecyclerView rvCarListings;
    private ImageButton homeButton, plusButton;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        initializeViews();
        setupRecyclerView();
        setupButtonListeners();
    }

    private void initializeViews() {
        rvCarListings = findViewById(R.id.rvCarListings);
        homeButton = findViewById(R.id.homeButton);
        plusButton = findViewById(R.id.plusButton);
        backButton = findViewById(R.id.back_watchList); // Ensure this ID exists in your layout
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvCarListings.setLayoutManager(layoutManager);

        UserSession userSession = UserSession.getInstance(this);
        User currentUser = userSession.getUser();

        List<CarListing> carListings = WatchlistHelper.getInstance().getWatchListUser(currentUser);
        Log.d("WatchListActivity", "Number of car listings: " + carListings.size());
        CarAdapterWatchlist adapter = CarAdapterWatchlist.getInstance(this);
        adapter.updateData(carListings);

        rvCarListings.setAdapter(adapter);
    }

    private void setupButtonListeners() {
        if (backButton != null) {
            backButton.setOnClickListener(v -> goBack());
        } else {
            // Log or handle the case where backButton is null
        }

        if (homeButton != null) {
            homeButton.setOnClickListener(v -> navigateToHome());
        }

        if (plusButton != null) {
            plusButton.setOnClickListener(v -> goCreateListing());
        }
    }

    private void navigateToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }

    private void goBack() {
        finish();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }

    private void goCreateListing() {
        Intent intent = new Intent(this, CreateListingActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }
}
