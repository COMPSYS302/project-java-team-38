package com.example.allgoods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private LinearLayout backToHome;
    private ImageView notificationButton;
    private ImageView editUserDetails;
    private LinearLayout manageAccountIcon;
    private LinearLayout managePaymentIcon;
    private LinearLayout watchlistIcon;
    private LinearLayout supportIcon;
    private LinearLayout signoutButton;

    private ImageButton homeButton, plusButton, watchlistmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initViews();
        setupListeners();
        navView();

        setupButtonListeners();
    }

    private void setupButtonListeners() {
        homeButton.setOnClickListener(v -> navigatetoHomePage());
        plusButton.setOnClickListener(v -> navigateToCreateListing());
        watchlistmenu.setOnClickListener(v -> navigateToWatchList());



    }

    private void navView() {
        homeButton = findViewById(R.id.homeButton);
        plusButton = findViewById(R.id.plusButton);
        watchlistmenu = findViewById(R.id.watchlistmenu);



    }

    private void initViews() {
        // Initialize UI components by their IDs
        backToHome = findViewById(R.id.profile_to_home_button);
        notificationButton = findViewById(R.id.notification_button);
        manageAccountIcon = findViewById(R.id.manageAccountButton);
        managePaymentIcon = findViewById(R.id.managePaymentButton);
        watchlistIcon = findViewById(R.id.watchlistButton);
        supportIcon = findViewById(R.id.supportButton);
        signoutButton = findViewById(R.id.signoutButton);
        editUserDetails = findViewById(R.id.edit_profile_button);

    }

    private void setupListeners() {
        // Back Image
        backToHome.setOnClickListener(v -> onBackImageClicked());

        // Notification Button
        notificationButton.setOnClickListener(v -> onNotificationButtonClicked());

        // Edit Profile Button
        editUserDetails.setOnClickListener(v -> onEditProfileButtonClicked());

        // Manage Account Icon
        manageAccountIcon.setOnClickListener(v -> onManageAccountClicked());

        // Manage Payment Icon
        managePaymentIcon.setOnClickListener(v -> onManagePaymentClicked());

        // Watchlist Icon
        watchlistIcon.setOnClickListener(v -> onWatchlistClicked());

        // Support Icon
        supportIcon.setOnClickListener(v -> onSupportClicked());

        // Signout Button
        signoutButton.setOnClickListener(v -> onSignoutClicked());
    }

    // Define methods for handling click events

    private void onBackImageClicked() {
        try {
            Intent backToMainIntent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(backToMainIntent);
        }catch (Exception e){
            Toast.makeText(ProfileActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
        }
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }

    private void onNotificationButtonClicked() {
        // Handle the notification button click event here
    }

    private void onEditProfileButtonClicked() {
        Intent editProfile = new Intent(ProfileActivity.this, EditProfileActivity.class);
        startActivity(editProfile);
    }

    private void onManageAccountClicked() {
        // Handle the manage account icon click event here
    }

    private void onManagePaymentClicked() {
        // Handle the manage payment icon click event here
    }

    private void onWatchlistClicked() {
        // Handle the watchlist icon click event here
    }

    private void onSupportClicked() {
        // Handle the support icon click event here
    }

    // onSignoutClicked() function reacts to signout button being clicked and signs user out and
    // brings them back to login page
    private void onSignoutClicked() {
        try {
            Intent backToLoginIntent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(backToLoginIntent);
            overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        }catch (Exception e){
            Toast.makeText(ProfileActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
        }
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

    private void navigatetoHomePage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }
}
