package com.example.allgoods;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginButton;
    Button signupText;
    AuthenticationManager authenticationManager = AuthenticationManager.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signupText = findViewById(R.id.signupText);

        signupText.setOnClickListener(view -> {

        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        });

// To do:
// login validation with data base
// once logged in go to main activity page
// convert username and password to string and validate whether fields are valid or not
        loginButton.setOnClickListener(view -> {
            try {
                if(authenticationManager.loginAuthenticator(username.getText().toString(), password.getText().toString()) && authenticationManager.databaseValidator()) {
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    User currentUser = authenticationManager.users.get(username.getText().toString());
                    UserSession session = UserSession.getInstance(LoginActivity.this);
                    session.createLoginSession(currentUser);
                    Intent moveToMainActivitiy = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(moveToMainActivitiy);
                    overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                }
            } catch(IllegalArgumentException e){
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}