package com.example.allgoods;

import android.os.Bundle;
import android.util.Log;
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

public class ItemCarListing extends AppCompatActivity {
    private Button addWatchList; // Following Java naming conventions, variable names should start with a lower case letter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_car_listing); // Ensure you have a corresponding layout file in your resources

        // Initialize your button
        addWatchList = findViewById(R.id.AddWatchList); // Ensure your layout has a Button with this ID

        // Set an OnClickListener to the button
        addWatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This is where you define what happens when the button is clicked
                // Example: Show a Toast message
                Log.d("ItemCarListing", "Button clicked!");
                Toast.makeText(ItemCarListing.this, "Added to Watchlist!", Toast.LENGTH_SHORT).show();

                // If you wanted to start a new Activity, you could do it like this:
                // Intent intent = new Intent(ItemCarListing.this, NextActivity.class);
                // startActivity(intent);
            }
        });
    }
}
