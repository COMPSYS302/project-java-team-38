package com.example.allgoods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager2.widget.ViewPager2;

public class IndepthListingActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private ImageSliderAdapter sliderAdapter;

    private Button buyNow, watchlistcardbutton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemlisting);

        // Initialize UI components
        Button inquireButton = findViewById(R.id.inquireBtn);
        final CardView inquiryCardView = findViewById(R.id.cvInquiry);
        ImageButton btnBack = findViewById(R.id.btnBack);
        ImageButton crossButton = findViewById(R.id.ivCross);
        ImageButton watchlistbtn = findViewById(R.id.watchlistbtn);
        Button buyNow = findViewById(R.id.btnBuyNow);
        Button watchlistcardbutton = findViewById(R.id.btnWatchlist);



        viewPager = findViewById(R.id.ivCarImageListingDepth);

        // Retrieve the Parcelable CarListing object
        CarListing carListing = getIntent().getParcelableExtra("CarListing");

        // Set up the image slider if CarListing is not null
        if (carListing != null) {
            setupTextViews(carListing);
            setupImageSlider(carListing);
        } else {
            Toast.makeText(this, "Car details not available.", Toast.LENGTH_LONG).show();
        }

        // Button functionalities
        btnBack.setOnClickListener(v -> finish());
        buyNow.setOnClickListener(v -> navigateToPaymentsPage());
        watchlistcardbutton.setOnClickListener(v -> navigateToWatchlist());
        watchlistbtn.setOnClickListener(v -> Toast.makeText(this, "Added to WatchList", Toast.LENGTH_SHORT).show());
        setupCardViewButton(inquireButton, inquiryCardView, true);
        setupCardViewButton(crossButton, inquiryCardView, false);



    }




    private void setupTextViews(CarListing carListing) {
        TextView tvMakeModel = findViewById(R.id.MakeofCar);
        TextView tvPrice = findViewById(R.id.tvAskingPrice);
        TextView tvYear = findViewById(R.id.YearofCar);
        TextView tvOdo = findViewById(R.id.MileageofCar);
        TextView tvDescription = findViewById(R.id.tvCarDescription);
        TextView tvNumberPlate = findViewById(R.id.NumberPlateofCar);
        TextView tvMenuCarModel = findViewById(R.id.tvCarModelCard);
        TextView tvMenuPrice = findViewById(R.id.tvPriceCard);
        TextView tvCarModelTitle = findViewById(R.id.tvCarModelTitle);

        tvMakeModel.setText(String.format("%s %s", carListing.getCar().getMake(), carListing.getCar().getModel()));
        tvPrice.setText(String.format("$%d", carListing.getPrice()));
        tvYear.setText(String.format("Year: %d", carListing.getCar().getYear()));
        tvOdo.setText(String.format("%d km", carListing.getCar().getOdo()));
        tvDescription.setText(carListing.getCarDescription());
        tvNumberPlate.setText(carListing.getCar().getNumberPlate());
        tvMenuCarModel.setText(String.format("%s %s", carListing.getCar().getMake(),carListing.getCar().getModel()));
        tvMenuPrice.setText(String.format("$%d", carListing.getPrice()));
        tvCarModelTitle.setText(String.format("%s %s", carListing.getCar().getMake(), carListing.getCar().getModel()));
    }

    private void setupImageSlider(CarListing carListing) {
        sliderAdapter = new ImageSliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        sliderAdapter.setImageUris(carListing.getImages()); // Assumes getImages() method in CarListing
    }

    private void setupCardViewButton(View button, final CardView cardView, final boolean show) {
        button.setOnClickListener(v -> {
            Animation animation = show ? AnimationUtils.loadAnimation(v.getContext(), R.anim.slide_up)
                    : AnimationUtils.loadAnimation(v.getContext(), R.anim.slide_down);
            if (show) {
                cardView.setVisibility(View.VISIBLE);
            } else {
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) { }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        cardView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) { }
                });
            }
            cardView.startAnimation(animation);
        });
    }
    private void navigateToPaymentsPage() {
        Intent intent = new Intent(this, PaymentsActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    private void navigateToWatchlist() {
        Intent intent = new Intent(this, Watchlist.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

}
