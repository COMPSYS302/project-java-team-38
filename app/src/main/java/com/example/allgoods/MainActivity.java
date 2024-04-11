package com.example.allgoods;
import android.text.Editable;
import android.text.TextWatcher;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    private Button backButton, homeButton, plusButton, watchlistButton, watchlistAdd;
    private ImageView ivNavigationButton;
    private LinearLayout categoriesLayout;
    private EditText searchEditText;

    private EditText searchProducts;

    RecyclerView rvCarListings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        categoriesLayout = findViewById(R.id.llCategoryButtons);
        searchEditText = findViewById(R.id.etSearchProducts);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // If search bar is empty, display 7 random categories
                if (s.toString().isEmpty()) {
                    updateFeaturedCategories(Category.getRandomCategories(7));
                } else {
                    // Otherwise, filter and update categories based on user input
                    List<Category> filteredCategories = Category.filterCategoriesBasedOnInput(s.toString());
                    if (!filteredCategories.isEmpty()) {
                        updateFeaturedCategories(filteredCategories);
                    } else {
                        // If no categories matched the input, keep or refresh the random categories
                        updateFeaturedCategories(Category.getRandomCategories(7));
                    }
                }
            }
        });

        // Get a dynamic list of categories. This could come from a database or a remote server.
        List<Category> categories = Category.getRandomCategories(5);
        for (Category category : categories) {
            Button categoryButton = new Button(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(8, 8, 8, 8);
            categoryButton.setLayoutParams(layoutParams);
            categoryButton.setText(category.getDisplayName());
            categoryButton.setOnClickListener(view -> {
                // Handle the category click here
                // For example, you might filter listings based on `category.name()`
            });

            categoriesLayout.addView(categoryButton);
        }



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
        //searchButton = findViewById(R.id.etSearchProducts);
        searchProducts = findViewById(R.id.etSearchProducts);
        homeButton = findViewById(R.id.homeButton);
        plusButton = findViewById(R.id.plusButton);
        watchlistButton = findViewById(R.id.watchlistButton);
        // Add Watchlist add

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

        searchProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the search results Activity
                Intent intent = new Intent(MainActivity.this, SearchResultsActivity.class);
                startActivity(intent);

                // Optional: Bring up the keyboard automatically
                if(v.requestFocus()) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
    }

    private void updateFeaturedCategories(List<Category> categories) {
        categoriesLayout.removeAllViews(); // Clear existing views

        for (Category category : categories) {
            Button categoryButton = new Button(this);
            categoryButton.setText(category.getDisplayName());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(8, 8, 8, 8); // Adjust margins as needed
            categoryButton.setLayoutParams(layoutParams);
            categoryButton.setOnClickListener(view -> {
                // Handle the category click here
            });
            categoriesLayout.addView(categoryButton);
        }
    }


}
