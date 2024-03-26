package com.example.allgoods;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.content.Intent;




public class MainActivity extends AppCompatActivity {
    private Button backButton, homeButton, plusButton, watchlistButton;
    private ImageView ivNavigationButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {





        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                // Action for Plus button
                Toast.makeText(MainActivity.this, "Plus clicked", Toast.LENGTH_SHORT).show();
                // Actions or Intent to add something could go here
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
