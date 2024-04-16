package com.example.allgoods;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

public class StartingPageActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;
    private Button signInButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_signin_signout_register);

        viewFlipper = findViewById(R.id.Flipper);
        signInButton = findViewById(R.id.sign_in_button);
        registerButton = findViewById(R.id.register_button);

        // Set up the button click listeners
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle sign-in button click
                Intent intent = new Intent(StartingPageActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle register button click
                Intent intent = new Intent(StartingPageActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
