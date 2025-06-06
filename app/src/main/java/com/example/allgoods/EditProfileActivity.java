package com.example.allgoods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initializeViews();
    }

    private void initializeViews() {
        Button changeUsernameButton = findViewById(R.id.changeUsernameButton);
        Button changePasswordButton = findViewById(R.id.changePasswordButton);
        ImageView backButton = findViewById(R.id.backButtonToUserDetails);

        changeUsernameButton.setOnClickListener(v -> handleChangeUsername());
        changePasswordButton.setOnClickListener(v -> handleChangePassword());
        backButton.setOnClickListener(v -> handleBack());
    }

    // handleChangeUsername() function takes the user to the
    // activity_change_username.xml
    private void handleChangeUsername() {
        Intent changeUsernameActivity = new Intent(EditProfileActivity.this, EditUsernameActivity.class);
        startActivity(changeUsernameActivity);

        // Apply the fade-in and fade-out animations
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void handleChangePassword() {
        Intent changeUsernameActivity = new Intent(EditProfileActivity.this, EditPasswordActivity.class);
        startActivity(changeUsernameActivity);

        // Apply the fade-in and fade-out animations
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void handleBack() {
        finish(); // Simply finish the activity to go back
    }
}
