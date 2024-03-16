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
    AuthenticationManager authenticationManager;
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
            public void onClick(View view) {
                try {
                    authenticationManager.loginAuthenticator(username.getText().toString(),password.getText().toString());

                } catch(IllegalArgumentException e){
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}