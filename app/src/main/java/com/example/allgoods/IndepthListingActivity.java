package com.example.allgoods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class IndepthListingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemlisting); // Replace with your layout file name

        // Set the back button click listener
        ImageButton btnBack = findViewById(R.id.btnBack);

        ImageButton watchlistbtn= findViewById(R.id.watchlistbtn);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the current activity and go back to the previous page
                Toast.makeText(IndepthListingActivity.this, "Back Button clicked", Toast.LENGTH_SHORT).show();
                Intent createListingStart = new Intent(IndepthListingActivity.this, MainActivity.class);
                startActivity(createListingStart);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            }
        });


        watchlistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the current activity and go back to the previous page
                Toast.makeText(IndepthListingActivity.this, "Added to WatchList", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
