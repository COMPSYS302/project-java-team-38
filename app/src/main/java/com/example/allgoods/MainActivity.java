package com.example.allgoods;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button backButton,  watchlistAdd;

    private ImageButton homeButton, plusButton, watchlistButton;
    private ImageView ivNavigationButton;
    private LinearLayout categoriesLayout;
    private EditText searchEditText, searchProducts;
    private RecyclerView rvCarListings;
    private CarAdapter carAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeUIComponents();
        setupRecyclerView();
        setupCategoryButtons();
        setupButtonListeners();
    }

    private void initializeUIComponents() {
        categoriesLayout = findViewById(R.id.llCategoryButtons);
        searchEditText = findViewById(R.id.etSearchProducts);
        searchProducts = findViewById(R.id.etSearchProducts);
        ivNavigationButton = findViewById(R.id.ivProfileImage);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                List<Category> filteredCategories = Category.filterCategoriesBasedOnInput(s.toString());
                if (!filteredCategories.isEmpty()) {
                    updateFeaturedCategories(filteredCategories);
                }
            }
        });
    }

    private void setupRecyclerView() {
        rvCarListings = findViewById(R.id.rvCarListings);
        CarDatabaseManager dbManager = CarDatabaseManager.getInstance();
        List<CarListing> carListings = dbManager.getAllListings();
        carAdapter = CarAdapter.getInstance(this);
        carAdapter.updateData(carListings);
        rvCarListings.setAdapter(carAdapter);
        rvCarListings.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupCategoryButtons() {
        List<Category> categories = Category.getRandomCategories(5);
        for (Category category : categories) {
            Button categoryButton = createCategoryButton(category);
            categoriesLayout.addView(categoryButton);
        }
    }

    private Button createCategoryButton(Category category) {
        Button categoryButton = new Button(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 8, 8, 8);
        categoryButton.setLayoutParams(layoutParams);
        categoryButton.setText(category.getDisplayName());
        categoryButton.setOnClickListener(view -> {
            Intent searchIntent = new Intent(MainActivity.this, SearchResultsActivity.class);
            searchIntent.putExtra("SEARCH_QUERY", category.getDisplayName());
            startActivity(searchIntent);
        });
        return categoryButton;
    }

    private void setupButtonListeners() {
        homeButton = findViewById(R.id.homeButton);
        plusButton = findViewById(R.id.plusButton);
        watchlistButton = findViewById(R.id.watchlistButton);

        ivNavigationButton.setOnClickListener(v -> navigateToProfile());

        homeButton.setOnClickListener(v -> showToast("Home clicked"));
        plusButton.setOnClickListener(v -> navigateToCreateListing());
        watchlistButton.setOnClickListener(v -> showToast("Watchlist clicked"));
        searchProducts.setOnClickListener(this::startSearchResultsActivity);
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

    private void startSearchResultsActivity(View view) {
        Intent intent = new Intent(this, SearchResultsActivity.class);
        startActivity(intent);
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void updateFeaturedCategories(List<Category> categories) {
        categoriesLayout.removeAllViews();
        for (Category category : categories) {
            Button categoryButton = createCategoryButton(category);
            categoriesLayout.addView(categoryButton);
        }
    }
}
