package com.example.allgoods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentsActivity extends AppCompatActivity {
    ImageButton backButton;

    Button confirmButton, cancelButton;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments_page);

        backButton = findViewById(R.id.btnBack);
        confirmButton = findViewById(R.id.btnConfirmPurchase);
        cancelButton = findViewById(R.id.btnCancel);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle register button click
                Intent intent = new Intent(PaymentsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle register button click
                Intent intent = new Intent(PaymentsActivity.this, PaymentSuccesful.class);
                startActivity(intent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle register button click
                Intent intent = new Intent(PaymentsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
