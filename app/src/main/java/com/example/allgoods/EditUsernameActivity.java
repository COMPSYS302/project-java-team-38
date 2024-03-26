package com.example.allgoods;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class EditUsernameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username); // Ensure the layout name is correct

        // Initialize the EditTexts
        EditText userEmailEditText = findViewById(R.id.userEmail);
        EditText oldUsernameEditText = findViewById(R.id.oldUsername);
        EditText newUsernameEditText = findViewById(R.id.newUsername);
        // instalise authenticationManager and UserHelper
        AuthenticationManager authManager = AuthenticationManager.getInstance();
        UserHelper userHelper = new UserHelper();

        // Intitialize the back button
        ImageView backButton = findViewById(R.id.backButtonToEditProfile);

        // function goes back to the editProfileActivity
        backButton.setOnClickListener(v -> {
            finish();
        });

        // Initialize the "Change Username" button
        Button changeUsernameButton = findViewById(R.id.newUsernameButton);

        // Set a click listener on the button
        changeUsernameButton.setOnClickListener(v -> {
            // Retrieve text from EditTexts and convert to String
            String userEmail = userEmailEditText.getText().toString();
            String oldUsername = oldUsernameEditText.getText().toString();
            String newUsername = newUsernameEditText.getText().toString();
            User user = authManager.users.get(userEmail);


            // Implementing validation for username change
            if(!authManager.users.containsKey(userEmail)){
                Toast.makeText(this, "Please input a valid email", Toast.LENGTH_SHORT).show();
            }
            if(userEmail.isEmpty()){
                Toast.makeText(this, "Please input an email address", Toast.LENGTH_SHORT).show();
            }
            assert user != null;
            if(!oldUsername.equals(user.getUsername())){
                Toast.makeText(this, "Old Username does not match", Toast.LENGTH_SHORT).show();
            }
            try{
                user.setUsername(userHelper.changeUsername(newUsername,user.getUsername()));
            }catch (IllegalArgumentException e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
            // shows username is changed
            Toast.makeText(this, "Hi: " + newUsername + " nice name change!", Toast.LENGTH_SHORT).show();
        });
    }
}
