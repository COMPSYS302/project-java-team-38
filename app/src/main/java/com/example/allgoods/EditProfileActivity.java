package com.example.allgoods;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {
    AuthenticationManager getUser;
    UserHelper userHelper;
    private EditText email;
    private EditText newUsernameEditText;
    private EditText oldPasswordEditText;
    private EditText newPasswordEditText1;
    private EditText newPasswordEditText2;
    private Button changeUsernameButton;
    private Button changePasswordButton;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initializeViews();
        setupButtonListeners();
        getUser = AuthenticationManager.getInstance();
        userHelper = new UserHelper();
    }

    private void initializeViews() {
        email = findViewById(R.id.email);
        newUsernameEditText = findViewById(R.id.newUsername);
        oldPasswordEditText = findViewById(R.id.oldPassword);
        newPasswordEditText1 = findViewById(R.id.newPassword1);
        newPasswordEditText2 = findViewById(R.id.newPassword2);
        changeUsernameButton = findViewById(R.id.changeUsernameButton);
        changePasswordButton = findViewById(R.id.newPasswordConfirm);
        backButton = findViewById(R.id.backButtonEditProfile);
    }

    private void setupButtonListeners() {
        changeUsernameButton.setOnClickListener(v -> onChangeUsernameClicked());
        changePasswordButton.setOnClickListener(v -> onChangePasswordClicked());
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void onChangeUsernameClicked() {
        String userEmail = email.getText().toString();
        String userNewUsername = newUsernameEditText.getText().toString();
        User user = getUser.users.get(userEmail);
        if(userEmail.isEmpty()){
            Toast.makeText(EditProfileActivity.this, "Please Enter your email", Toast.LENGTH_SHORT).show();
        }


    }

    private void onChangePasswordClicked() {
        // TODO: Validate old passwords match
        // TODO: Implement your logic for changing the password here
    }

    @Override
    public void onBackPressed() {
        // TODO: Any additional logic before closing the activity (if necessary)
        super.onBackPressed(); // Closes the current activity and returns to the previous one
    }
}
