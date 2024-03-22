package com.example.allgoods;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    EditText email;
    EditText username;
    EditText password;
    Button registerButton;

    Button returnButton;

    AuthenticationManager authenticationManager = AuthenticationManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.editTextEmail);
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        registerButton = findViewById(R.id.buttonRegister);
        returnButton = findViewById(R.id.returnButton);

        returnButton.setOnClickListener(view -> {

            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        registerButton.setOnClickListener(view -> {
            // Retrieve user input
            String userEmail = email.getText().toString();
            String userUsername = username.getText().toString();
            String userPassword = password.getText().toString();

            // Validate input
            if (userEmail.isEmpty() || userUsername.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create a new user
            try {
                UUID uniqueID = UUID.randomUUID();
                User newUser = new User(uniqueID.toString(),userUsername, userPassword, userEmail);
                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                // adds the user to the authentication Manager
                authenticationManager.addUserCredentials(newUser);
                // Navigate to the login page after successful registration
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            } catch (IllegalArgumentException e) {
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
