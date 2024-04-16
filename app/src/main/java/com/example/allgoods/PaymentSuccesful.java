package com.example.allgoods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentSuccesful extends AppCompatActivity {
    ImageButton backButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_confirmation);

        backButton = findViewById(R.id.btnBack);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle register button click
                Intent intent = new Intent(PaymentSuccesful.this, PaymentsActivity.class);
                startActivity(intent);
            }
        });

    }
}