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

        signupText.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            // logic for login written over here
            // To do:
            // login validation with data base
            // once logged in go to main activity page
            // convert username and password to string and validate whether fields are valid or not
            public void onClick(View view) {
                try {
                    if(authenticationManager.loginAuthenticator(username.getText().toString(),password.getText().toString()) == true && authenticationManager.databaseValidator() == true) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent moveToMainActivitiy = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(moveToMainActivitiy);
                    }
                } catch(IllegalArgumentException e){
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}