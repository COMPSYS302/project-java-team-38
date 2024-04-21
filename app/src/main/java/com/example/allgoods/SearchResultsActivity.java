package com.example.allgoods;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchResultsActivity extends AppCompatActivity implements SearchAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private EditText searchEditText;

    private Button hatchback, sedan, suv, coupe, minivan, other;
    private ImageButton homeButton, plusButton, watchlistButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initializeUIComponents();
        initializeAdapter();
        setupButtonListeners();
        setupSearchListener();
    }

    private void initializeUIComponents() {
        searchEditText = findViewById(R.id.etSearchProducts);
        recyclerView = findViewById(R.id.rvCarListingsSearches);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        hatchback = findViewById(R.id.search_hatchback);
        sedan = findViewById(R.id.search_sedan);
        suv = findViewById(R.id.search_suv);
        coupe = findViewById(R.id.search_coupe);
        minivan = findViewById(R.id.search_minivan);
        other = findViewById(R.id.search_other);
        homeButton = findViewById(R.id.homeButtonSearch);
        plusButton = findViewById(R.id.plusButtonSearch);
        watchlistButton = findViewById(R.id.watchlistButtonSearch);

        String searchQuery = getIntent().getStringExtra("searchQuery");
        searchEditText.setText(searchQuery);
    }

    private void initializeAdapter() {
        List<CarListing> carListings = CarDatabaseManager.getInstance().getAllListings();

        searchAdapter = new SearchAdapter(this, carListings, this::onAddWatchClick);
        recyclerView.setAdapter(searchAdapter);
        if (searchEditText.getText().toString().length() >= 1) {
            searchAdapter.getFilter().filter(searchEditText.getText().toString());
        }
    }

    @Override
    public void onAddWatchClick(int position) {
        CarListing carListing = searchAdapter.getItem(position);
        if (carListing != null) {
            User user = UserSession.getInstance(this).getUser();
            user.addViewedCarListing(carListing);
            navigateToCarDetails(carListing);
        } else {
            showToast("Error accessing listing details");
        }
    }

    private void navigateToCarDetails(CarListing carListing) {
        Intent intent = new Intent(this, IndepthListingActivity.class);
        intent.putExtra("CarListing", carListing);
        startActivity(intent);
    }

    private void setupButtonListeners() {
        homeButton.setOnClickListener(v -> navigateToMainPage());
        plusButton.setOnClickListener(v -> navigateToCreateListing());
        watchlistButton.setOnClickListener(v -> navigateToWatchList());
        hatchback.setOnClickListener(v -> onCategoryClicked("Hatchback"));
        sedan.setOnClickListener(v -> onCategoryClicked("Sedan"));
        suv.setOnClickListener(v -> onCategoryClicked("Suv"));
        coupe.setOnClickListener(v -> onCategoryClicked("Coupe"));
        minivan.setOnClickListener(v -> onCategoryClicked("Minivan"));
        other.setOnClickListener(v -> onCategoryClicked("Other"));
    }

    private void onCategoryClicked(String category) {
        searchEditText.setText(category);
        searchAdapter.getFilter().filter(category);
    }

    private void setupSearchListener() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                searchAdapter.getFilter().filter(s.toString());
            }
        });
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

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
