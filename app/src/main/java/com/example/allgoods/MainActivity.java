package com.example.allgoods;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CarAdapter.OnItemClickListener {
    private Button backButton, homeButton, plusButton, watchlistButton;
    private ImageView ivNavigationButton;
    private LinearLayout categoriesLayout;
    private EditText searchEditText;
    private RecyclerView rvCarListings;
    private CarAdapter carAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUIComponents();
        setupRecyclerView();
    }

    private void initializeUIComponents() {
        categoriesLayout = findViewById(R.id.llCategoryButtons);
        searchEditText = findViewById(R.id.etSearchProducts);
        ivNavigationButton = findViewById(R.id.ivProfileImage);
        homeButton = findViewById(R.id.homeButton);
        plusButton = findViewById(R.id.plusButton);
        watchlistButton = findViewById(R.id.watchlistButton);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });

        setupButtonListeners();
    }

    private void setupRecyclerView() {
        rvCarListings = findViewById(R.id.rvCarListings);
        CarDatabaseManager dbManager = CarDatabaseManager.getInstance();
        List<CarListing> carListings = dbManager.getAllListings();
        carAdapter = CarAdapter.getInstance(this, this);
        carAdapter.updateData(carListings);
        rvCarListings.setAdapter(carAdapter);
        rvCarListings.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupButtonListeners() {
        ivNavigationButton.setOnClickListener(v -> navigateToProfile());
        homeButton.setOnClickListener(v -> showToast("Home clicked"));
        plusButton.setOnClickListener(v -> navigateToCreateListing());
        watchlistButton.setOnClickListener(v -> navigateToWatchList());
    }

    private void navigateToProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    private void navigateToCreateListing() {
        Intent intent = new Intent(this, CreateListingActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    private void navigateToWatchList() {
        Intent intent = new Intent(this, WatchListActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddWatchClick(int position) {
        CarListing carListing = carAdapter.getItem(position);
        if (carListing != null) {
            UserSession userSession = UserSession.getInstance(this);
            User user = userSession.getUser();
            WatchlistHelper.getInstance().addToWatchlist(user, carListing);
            Toast.makeText(this, "Added to watchlist: " + carListing.getCar().getModel(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error adding to Watchlist", Toast.LENGTH_SHORT).show();
        }
    }

}
