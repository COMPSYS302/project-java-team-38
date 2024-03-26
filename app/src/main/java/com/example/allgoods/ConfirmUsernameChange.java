package com.example.allgoods;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class ConfirmUsernameChange extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username_change_confirmed); // Replace 'your_xml_layout_name' with the name of your XML layout file

        // Using a Handler to introduce a delay
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(ConfirmUsernameChange.this, ProfileActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out); // Custom animation
            finish(); // Finish current activity
        }, 2000); // 2000 ms delay for 2 seconds
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);; // Apply the slide-down animation when finishing the activity
    }
}
