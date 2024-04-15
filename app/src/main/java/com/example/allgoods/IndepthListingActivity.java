package com.example.allgoods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class IndepthListingActivity extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemlisting); // Replace with your layout file name

        Button inquireButton = findViewById(R.id.inquireBtn);
        final CardView inquiryCardView = findViewById(R.id.cvInquiry);

        // Set the back button click listener
        ImageButton btnBack = findViewById(R.id.btnBack);

        ImageButton watchlistbtn= findViewById(R.id.watchlistbtn);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the current activity and go back to the previous page
                Toast.makeText(IndepthListingActivity.this, "Back Button clicked", Toast.LENGTH_SHORT).show();
                Intent createListingStart = new Intent(IndepthListingActivity.this, MainActivity.class);
                startActivity(createListingStart);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            }
        });


        watchlistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the current activity and go back to the previous page
                Toast.makeText(IndepthListingActivity.this, "Added to WatchList", Toast.LENGTH_SHORT).show();

            }
        });

        //iNQUIRE BUTTON ONCLICK CARD VIEW POP UP
        inquireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inquiryCardView.getVisibility() == View.GONE) {
                    Animation slideUp = AnimationUtils.loadAnimation(v.getContext(), R.anim.slide_up); // Use v.getContext() to get the context from the view
                    inquiryCardView.startAnimation(slideUp);
                    inquiryCardView.setVisibility(View.VISIBLE);
                } else {
                    Animation slideDown = AnimationUtils.loadAnimation(v.getContext(), R.anim.slide_down); // Use v.getContext() to get the context from the view
                    slideDown.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            // Animation started event
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            inquiryCardView.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            // Animation repeat event
                        }
                    });
                    inquiryCardView.startAnimation(slideDown);
                }
            }
        });

    }
}
