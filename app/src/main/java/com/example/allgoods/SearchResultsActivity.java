package com.example.allgoods;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private EditText searchEditText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchEditText = findViewById(R.id.etSearchProducts);

        recyclerView = findViewById(R.id.rvCarListingsSearches);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initializeAdapter();

        String searchQuery = getIntent().getStringExtra("searchQuery");
        if (searchQuery != null && !searchQuery.isEmpty()) {
            searchEditText.setText(searchQuery);
            searchAdapter.getFilter().filter(searchQuery);
        }

        setupSearchListener();
    }

    private void initializeAdapter() {
        List<CarListing> carListings = CarDatabaseManager.getInstance().getAllListings();
        searchAdapter = new SearchAdapter(this, carListings, position -> {
            // Handle click event, e.g., adding to watchlist
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
}
