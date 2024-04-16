package com.example.allgoods;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity implements SearchAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private EditText searchEditText;

    private Button hatchback, sedan, suv, coupe, minivan, other;
    private ImageButton homeButton, plusButton, watchlistButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchEditText = findViewById(R.id.etSearchProducts);
        recyclerView = findViewById(R.id.rvCarListingsSearches);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String searchQuery = getIntent().getStringExtra("searchQuery");
        try {
            if (searchQuery != null && !searchQuery.isEmpty()) {
                searchAdapter.getFilter().filter(searchQuery);
            }
        }catch (NullPointerException e){
            showToast("No Current Listings Matching Search");
        }
        searchEditText.setText(searchQuery);
        initializeUIComponents();
        initializeAdapter();
        setupButtonListeners();
        setupSearchListener();
    }

    private void initializeUIComponents() {
        hatchback = findViewById(R.id.search_hatchback);
        sedan = findViewById(R.id.search_sedan);
        suv = findViewById(R.id.search_suv);
        coupe = findViewById(R.id.search_coupe);
        minivan = findViewById(R.id.search_minivan);
        other = findViewById(R.id.search_other);
        homeButton = findViewById(R.id.homeButtonSearch);
        plusButton = findViewById(R.id.plusButtonSearch);
        watchlistButton = findViewById(R.id.watchlistButtonSearch);
    }

    private void setupButtonListeners() {
        hatchback.setOnClickListener(v -> onHatchbackClicked());
        sedan.setOnClickListener(v -> onSedanClicked());
        suv.setOnClickListener(v -> onSuvClicked());
        coupe.setOnClickListener(v -> onCoupeClicked());
        minivan.setOnClickListener(v -> onMinivanClicked());
        other.setOnClickListener(v -> onOtherClicked());
        homeButton.setOnClickListener(v -> navigateToMainPage());
        plusButton.setOnClickListener(v -> navigateToCreateListing());
        watchlistButton.setOnClickListener(v -> navigateToWatchList());
    }

    @Override
    public void onAddWatchClick(int position) {
        CarListing carListing = searchAdapter.getItem(position);
        if (carListing != null) {
            User user = UserSession.getInstance(this).getUser();
            Log.d("SearchActivity", "User got: " + user.getUsername());
            WatchlistHelper.getInstance().addToWatchlist(user, carListing);
            Log.d("SearchActivity", "Listing added to watchlist: " + carListing.getCar().getModel());
            Toast.makeText(this, "Added to watchlist: " + carListing.getCar().getModel(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error adding to Watchlist", Toast.LENGTH_SHORT).show();
            Log.d("SearchActivity", "Failed to add to Watchlist");
        }
    }

    private void initializeAdapter() {
        List<CarListing> carListings = CarDatabaseManager.getInstance().getAllListings();
        searchAdapter = new SearchAdapter(this, carListings, position -> {
            onAddWatchClick(position);
            Toast.makeText(this, "Added to watchlist: " + searchAdapter.getItem(position).getCar().getModel(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(searchAdapter);
    }

    private List<CarListing> fetchCarListings() {

        return new ArrayList<>();
    }

    private void setupSearchListener() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                searchAdapter.getFilter().filter(s.toString());
            }
        });
    }



    private void navigateToMainPage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        showToast("Home Page");
    }

    private void navigateToCreateListing() {
        Intent intent = new Intent(this, CreateListingActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        showToast("Create Listing");
    }

    private void navigateToWatchList() {
        Intent intent = new Intent(this, WatchListActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        showToast("Watchlist");
    }



    private void onHatchbackClicked(){
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra("searchQuery", "Hatchback");
        startActivity(intent);
    }

    private void onSedanClicked(){
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra("searchQuery", "Sedan");
        startActivity(intent);
    }

    private void onSuvClicked(){
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra("searchQuery", "Suv");
        startActivity(intent);
    }

    private void onCoupeClicked(){
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra("searchQuery", "Coupe");
        startActivity(intent);
    }

    private void onMinivanClicked(){
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra("searchQuery", "Minivan");
        startActivity(intent);
    }
    private void onOtherClicked(){
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra("searchQuery", "Other");
        startActivity(intent);
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
