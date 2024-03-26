package com.example.allgoods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditPasswordActivity extends AppCompatActivity {

    private EditText userEmailEditText;
    private EditText oldPasswordEditText;
    private EditText newPasswordEditText;
    private EditText confirmNewPasswordEditText;
    private Button changePasswordButton;
    private ImageView backButton;

    // Instance variables for AuthenticationManager and UserHelper
    private AuthenticationManager authManager;
    private UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password); // Ensure this matches your actual XML file name

        initViews();
        setupListeners();
    }

    private void initViews() {
        // Initialize UI components
        userEmailEditText = findViewById(R.id.userEmail);
        oldPasswordEditText = findViewById(R.id.oldPassword);
        newPasswordEditText = findViewById(R.id.newPassword);
        confirmNewPasswordEditText = findViewById(R.id.confirmNewPassword);
        changePasswordButton = findViewById(R.id.newPasswordConfirmation);
        backButton = findViewById(R.id.backButtonToEditProfile);

        // Initialize AuthenticationManager and UserHelper
        authManager = AuthenticationManager.getInstance();
        userHelper = new UserHelper();
    }

    private void setupListeners() {
        backButton.setOnClickListener(this::onBackButtonClicked);
        changePasswordButton.setOnClickListener(this::onChangePasswordButtonClicked);
    }

    private void onBackButtonClicked(View v) {
        finish(); // Close the current activity and return to the previous one

        // Apply the fade-in and fade-out animations for the transition
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void onChangePasswordButtonClicked(View v) {
        String userEmail = userEmailEditText.getText().toString();
        String oldPassword = oldPasswordEditText.getText().toString();
        String newPassword = newPasswordEditText.getText().toString();
        String confirmNewPassword = confirmNewPasswordEditText.getText().toString();

        // Validate input fields
        if (userEmail.isEmpty() || oldPassword.isEmpty() || newPassword.isEmpty() || !newPassword.equals(confirmNewPassword)) {
            Toast.makeText(this, "Please fill in all fields and ensure new passwords match", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = authManager.users.get(userEmail);
        if (user == null) {
            Toast.makeText(this, "Email not valid", Toast.LENGTH_SHORT).show();
        }
        // Attempt to change the password
        try {
            user.setPassword(oldPassword, newPassword, confirmNewPassword);
            Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show();

            // Optionally, redirect the user or perform other actions after successful password change
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
