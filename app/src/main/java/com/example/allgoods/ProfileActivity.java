package com.example.allgoods;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private ImageView photoImageView;
    private TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to the XML layout provided
        setContentView(R.layout.activity_profile); // Ensure you have activity_profile.xml in your layout resources

        // Initialize UI components
        initViews();

        // Set up click listeners
        setupListeners();
    }

    private void initViews() {
        // Initialize your ImageView and TextView by finding them by their IDs
        photoImageView = findViewById(R.id.user_image);
        titleTextView = findViewById(R.id.homeButton);
    }

    private void setupListeners() {
        // Set an OnClickListener for the ImageView
        photoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the ImageView click event here
                onPhotoImageViewClicked();
            }
        });

        // Set an OnClickListener for the TextView

    }

    private void onPhotoImageViewClicked() {
        // This function is called when the ImageView is clicked
        // You can start a new activity, open a dialog, etc.
    }

    private void onTitleTextViewClicked() {
        // This function is called when the TextView is clicked
        // You can start a new activity, open a dialog, etc.
    }
}
