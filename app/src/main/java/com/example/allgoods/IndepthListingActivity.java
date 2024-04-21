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

    private CarListing currentCarListing;
    private ImageButton homeButton, plusButton, watchlistButton;


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
        homeButton = findViewById(R.id.homeButton);
        plusButton = findViewById(R.id.plusButton);
        watchlistButton = findViewById(R.id.watchlistButton);


        animateButton(buyNow);
        animateButton(watchlistcardbutton);

        CarListing carListing = getIntent().getParcelableExtra("CarListing");
        currentCarListing = carListing;

        if (carListing != null) {
            setupTextViews(carListing);
            setupImageSlider(carListing);
        } else {
            Toast.makeText(this, "Car details not available.", Toast.LENGTH_LONG).show();
        }

        // Button functionalities
        homeButton.setOnClickListener(v -> navigateToMainPage());
        plusButton.setOnClickListener(v -> navigateToCreateListing());
        watchlistButton.setOnClickListener(v -> navigateToWatchList());
        btnBack.setOnClickListener(v -> finish());
        buyNow.setOnClickListener(v -> navigateToPaymentsPage());
        watchlistcardbutton.setOnClickListener(v -> addToWatchList());
        watchlistbtn.setOnClickListener(v -> Toast.makeText(this, "Added to WatchList", Toast.LENGTH_SHORT).show());
        setupCardViewButton(inquireButton, inquiryCardView, true);
        setupCardViewButton(crossButton, inquiryCardView, false);

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
        sliderAdapter.setImageUris(carListing.getImages());
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
        CarListing carListing = getIntent().getParcelableExtra("CarListing");
        if (carListing != null) {
            Intent intent = new Intent(this, PaymentsActivity.class);
            intent.putExtra("CarListing", carListing);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        } else {
            Toast.makeText(this, "Error: Car details not available.", Toast.LENGTH_SHORT).show();
        }
    }


    private void addToWatchList() {
        if (currentCarListing != null) {
            UserSession userSession = UserSession.getInstance(this);
            User user = userSession.getUser();
            WatchlistHelper.getInstance().addToWatchlist(user, currentCarListing);
            Toast.makeText(this, "Added to watchlist: " + currentCarListing.getCar().getModel(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error adding to Watchlist", Toast.LENGTH_SHORT).show();
        }

    }

    private void animateButton(Button button) {
        // Create fade-in animation
        Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
        fadeIn.setDuration(500); // Customize duration as needed

        // Create fade-out animation
        Animation fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
        fadeOut.setDuration(500); // Customize duration as needed

        // Set click listener for the button
        button.setOnClickListener(v -> {
            // Start fade-in animation on button click
            button.startAnimation(fadeIn);
        });

        // Set up animation listener for fade-in animation
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Optional: Actions to take when fade-in starts
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Start fade-out animation once fade-in completes
                button.startAnimation(fadeOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Optional: Actions to take if animation repeats
            }
        });

        // Set up animation listener for fade-out animation
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Optional: Actions to take when fade-out starts
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Optional: Actions to take when fade-out ends
                // You can loop the animation here if needed by starting another animation
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Optional: Actions to take if animation repeats
            }
        });
    }



}
