package com.example.allgoods;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WatchListActivity extends AppCompatActivity {

    private RecyclerView rvCarListings;
    private Button homeButton, plusButton, watchlistButton;

    UserSession userSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist); // Ensure the layout file name matches

        initializeViews();
        setupRecyclerView();
        setupButtonListeners();
    }

    private void initializeViews() {
        rvCarListings = findViewById(R.id.rvCarListings);
        homeButton = findViewById(R.id.homeButton);
        plusButton = findViewById(R.id.plusButton);
        watchlistButton = findViewById(R.id.watchlistmenu);
    }

    private void setupRecyclerView() {
        // Setup the RecyclerView with a LinearLayoutManager and an adapter
        User user = userSession.getUser();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvCarListings.setLayoutManager(layoutManager);

        // Assuming you have a method to get your data:
        List<CarListing> carListings = WatchlistHelper.getInstance().getWatchListUser(user); // currentUser needs to be defined or passed
        //CarAdapter adapter = new CarAdapter(this, carListings);
       // rvCarListings.setAdapter(adapter);
    }

    private void setupButtonListeners() {
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Home button click
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Plus button click
            }
        });

        watchlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Watchlist button click
            }
        });
    }
}
