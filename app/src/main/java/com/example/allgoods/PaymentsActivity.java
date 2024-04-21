package com.example.allgoods;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class PaymentsActivity extends AppCompatActivity {
    ImageButton backButton;

    Button confirmButton, cancelButton;

    private ViewPager2 viewPager;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments_page);

        backButton = findViewById(R.id.btnBack);
        confirmButton = findViewById(R.id.btnConfirmPurchase);
        cancelButton = findViewById(R.id.btnCancel);

        CarListing carListing = getIntent().getParcelableExtra("CarListing");

        if (carListing != null) {
            setupTextViews(carListing);
        } else {
            Toast.makeText(this, "Car details not available.", Toast.LENGTH_LONG).show();
        }


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

    @SuppressLint("DefaultLocale")
    private void setupTextViews(CarListing carListing) {
        TextView tvMakeModel = findViewById(R.id.CarModelAndBrand);

        tvMakeModel.setText(String.format("%s %s", carListing.getCar().getMake(), carListing.getCar().getModel()));


    }


}
