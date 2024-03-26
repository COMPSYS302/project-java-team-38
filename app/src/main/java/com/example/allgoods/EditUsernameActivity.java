package com.example.allgoods;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditUsernameActivity extends AppCompatActivity {

    private EditText userEmailEditText;
    private EditText oldUsernameEditText;
    private EditText newUsernameEditText;
    private Button changeUsernameButton;
    private ImageView backButton;

    // Instance variables for AuthenticationManager and UserHelper
    private AuthenticationManager authManager;
    private UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);

        initViews();
        setupListeners();
    }

    private void initViews() {
        // Initialize UI components
        userEmailEditText = findViewById(R.id.userEmail);
        oldUsernameEditText = findViewById(R.id.oldUsername);
        newUsernameEditText = findViewById(R.id.newUsername);
        changeUsernameButton = findViewById(R.id.newUsernameButton);
        backButton = findViewById(R.id.backButtonToEditProfile);

        // Initialize AuthenticationManager and UserHelper
        authManager = AuthenticationManager.getInstance();
        userHelper = new UserHelper();
    }

    private void setupListeners() {
        backButton.setOnClickListener(this::onBackButtonClicked);
        changeUsernameButton.setOnClickListener(this::onChangeUsernameButtonClicked);
    }

    private void onBackButtonClicked(View v) {
        finish();

        // Apply the fade-in and fade-out animations
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void onChangeUsernameButtonClicked(View v) {
        String userEmail = userEmailEditText.getText().toString();
        String oldUsername = oldUsernameEditText.getText().toString();
        String newUsername = newUsernameEditText.getText().toString();
        User user = authManager.users.get(userEmail);

        // Validate inputs
        if (userEmail.isEmpty() || oldUsername.isEmpty() || newUsername.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!authManager.users.containsKey(userEmail)) {
            Toast.makeText(this, "Please input a valid email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!oldUsername.equals(user.getUsername())) {
            Toast.makeText(this, "Old Username does not match", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            user.setUsername(userHelper.changeUsername(newUsername, user.getUsername()));
            Toast.makeText(this, "Hi: " + newUsername + ", nice name change!", Toast.LENGTH_SHORT).show();

            // Handler to introduce a delay
            new Handler().postDelayed(() -> {
                Intent usernameConfirmChange = new Intent(EditUsernameActivity.this, ConfirmUsernameChange.class);
                startActivity(usernameConfirmChange);
                // Apply the custom animation
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }, 500); // 0.5 second delay

        } catch (IllegalArgumentException e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
