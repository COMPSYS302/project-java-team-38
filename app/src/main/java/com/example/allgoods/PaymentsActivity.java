package com.example.allgoods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class PaymentsActivity extends AppCompatActivity {
    private ImageButton backButton;
    private Button confirmButton;
    private Button cancelButton;
    private ImageSliderAdapter sliderAdapter;
    private ViewPager2 viewPager;

    private ImageButton homeButton, plusButton, watchlistButton;

    CarListing carListingToSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments_page);
        initViews();
        setupListeners();
        loadCarDetails();
    }

    private void initViews() {
        backButton = findViewById(R.id.btnBack);
        confirmButton = findViewById(R.id.btnConfirmPurchase);
        cancelButton = findViewById(R.id.btnCancel);
        viewPager = findViewById(R.id.ivCarImage1);
        homeButton = findViewById(R.id.homeButton);
        plusButton = findViewById(R.id.plusButton);
        watchlistButton = findViewById(R.id.watchlistButton);
    }

    private void setupListeners() {
        backButton.setOnClickListener(v -> navigateTo(MainActivity.class));
        confirmButton.setOnClickListener(v -> onConfirmPayment());
        cancelButton.setOnClickListener(v -> navigateTo(MainActivity.class));
        homeButton.setOnClickListener(v -> navigateToMainPage());
        plusButton.setOnClickListener(v -> navigateToCreateListing());
        watchlistButton.setOnClickListener(v -> navigateToWatchList());
    }

    private void navigateToMainPage() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }

    private void navigateToCreateListing() {
        startActivity(new Intent(this, CreateListingActivity.class));
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    private void navigateToWatchList() {
        startActivity(new Intent(this, WatchListActivity.class));
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    private void loadCarDetails() {
        CarListing carListing = getIntent().getParcelableExtra("CarListing");
        if (carListing != null) {
            displayCarDetails(carListing);
            setupImageSlider(carListing);
            carListingToSend = carListing;
        } else {
            Toast.makeText(this, "Car details not available.", Toast.LENGTH_LONG).show();
        }
    }

    private void displayCarDetails(CarListing carListing) {
        TextView tvMakeModel = findViewById(R.id.CarMakePay);
        TextView tvPrice = findViewById(R.id.tvAskingPrice);
        TextView tvYear = findViewById(R.id.tvYear);
        TextView tvOdo = findViewById(R.id.MileageofCar);
        TextView tvNumberPlate = findViewById(R.id.NumberPlateofCar);
        TextView tvMenuCarModel = findViewById(R.id.tvCarModelCard);
        TextView tvMenuPrice = findViewById(R.id.tvAskingPrice);
        TextView tvCarModelTitle = findViewById(R.id.CarModelAndBrand);

        tvMakeModel.setText(String.format("%s", carListing.getCar().getMake()));
        tvPrice.setText(String.format("$%d", carListing.getPrice()));
        tvYear.setText(String.format("Year: %d", carListing.getCar().getYear()));
        tvOdo.setText(String.format("%d km", carListing.getCar().getOdo()));
        tvNumberPlate.setText(carListing.getCar().getNumberPlate());
        tvMenuCarModel.setText(String.format("%s %s", carListing.getCar().getMake(),carListing.getCar().getModel()));
        tvMenuPrice.setText(String.format("$%d", carListing.getPrice()));
        tvCarModelTitle.setText(String.format("%s %s", carListing.getCar().getMake(), carListing.getCar().getModel()));
    }

    private void setupImageSlider(CarListing carListing) {
        sliderAdapter = new ImageSliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        sliderAdapter.setImageUris(carListing.getImages());
    }

    private void navigateTo(Class<?> cls) {
        Intent intent = new Intent(PaymentsActivity.this, cls);
        startActivity(intent);
    }

    private void onConfirmPayment(){
        EditText nameInput = findViewById(R.id.etBillingName);
        EditText cardNumberInput = findViewById(R.id.etCardNumber);
        EditText expiryDateInput = findViewById(R.id.etExpirationDate);
        EditText cvvInput = findViewById(R.id.etCvv);

        String name = nameInput.getText().toString();
        String cardNumber = cardNumberInput.getText().toString();
        String expiryDate = expiryDateInput.getText().toString();
        String cvv = cvvInput.getText().toString();

        if (isValidCreditCard(name, cardNumber, expiryDate, cvv)) {
            Intent intent = new Intent(this, PaymentSuccesful.class);
            intent.putExtra("CarListing", carListingToSend);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        } else {
            Toast.makeText(this, "Invalid credit card details", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isValidCreditCard(String name, String cardNumber, String expiry, String cvv) {
        for (CreditCardDetails cardDetail : CreditCardDetails.values()) {
            if (cardDetail.getCardHolderName().equalsIgnoreCase(name)
                    && cardDetail.getCardNumber().equals(cardNumber)
                    && cardDetail.getExpiryDate().equals(expiry)
                    && cardDetail.getCvv().equals(cvv)) {
                return true;
            }
        }
        return false;
    }
}
