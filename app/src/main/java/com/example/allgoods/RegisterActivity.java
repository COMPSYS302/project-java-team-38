package com.example.allgoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.UUID;

// RegisterActivity provides a user interface for new users to register by entering their email, username, and password.
public class RegisterActivity extends AppCompatActivity {

    // UI components for email, username, password inputs, and buttons for registration and returning to the login page
    EditText email;
    EditText username;
    EditText password;
    Button registerButton;
    Button returnButton;
    ImageButton backButton;

    // Instance of AuthenticationManager to handle user authentication tasks
    AuthenticationManager authenticationManager = AuthenticationManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initializing UI components
        email = findViewById(R.id.editTextEmail);
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        registerButton = findViewById(R.id.buttonRegister);
        returnButton = findViewById(R.id.returnButton);
        backButton  = findViewById(R.id.ButtonReturn);

        // Listener for the return button to navigate back to the LoginActivity
        returnButton.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        });

        // Listener for the register button to handle the registration process
        registerButton.setOnClickListener(view -> {
            // Retrieve user input from EditText fields
            String userEmail = email.getText().toString();
            String userUsername = username.getText().toString();
            String userPassword = password.getText().toString();

            // Validate user input to ensure no fields are empty
            if (userEmail.isEmpty() || userUsername.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return; // Exit the method if any field is empty
            }

            // Attempt to create a new user with the provided details
            try {
                // Generate a unique ID for the new user
                UUID uniqueID = UUID.randomUUID();
                // Create a new User object with the provided details
                User newUser = new User(uniqueID.toString(), userUsername, userPassword, userEmail);
                // Add the new user's credentials to the AuthenticationManager
                authenticationManager.addUserCredentials(newUser);
                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                // Navigate to the LoginActivity upon successful registration
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            } catch (IllegalArgumentException e) {
                // Display error message if user creation fails due to invalid input
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle register button click
                Intent intent = new Intent(RegisterActivity.this, StartingPageActivity.class);
                startActivity(intent);
            }
        });
    }
}
