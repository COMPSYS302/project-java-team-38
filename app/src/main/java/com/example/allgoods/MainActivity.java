package com.example.allgoods;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;



public class MainActivity extends AppCompatActivity implements CarAdapter.OnItemClickListener {
    private Button backButton, watchlistAdd;

    private ImageButton homeButton, plusButton, watchlistButton;

    private Button hatchback, sedan, suv, coupe, minivan, other;

    private ImageView ivNavigationButton;
    private LinearLayout categoriesLayout;
    private EditText searchEditText;
    private RecyclerView rvCarListings;
    private CarAdapter carAdapter;

    CarDatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DemoDataInitializer demoDataInitializer = DemoDataInitializer.getInstance(this);
        if(!demoDataInitializer.initializeDemoDataIfNecessary()) {
            initializeDemoDataInBackground();

    }
    initializeUIComponents();
    setupRecyclerView();

    }

    private void initializeDemoDataInBackground() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    initializeDemoData();
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setupRecyclerView();
                        }
                    });
                }
            }
        });

        executor.shutdown();
    }


    private void initializeUIComponents() {
        categoriesLayout = findViewById(R.id.llCategoryButtons);
        searchEditText = findViewById(R.id.etSearchProducts);
        ivNavigationButton = findViewById(R.id.ivProfileImage);
        homeButton = findViewById(R.id.homeButton);
        plusButton = findViewById(R.id.plusButton);
        watchlistButton = findViewById(R.id.watchlistButton);
        hatchback = findViewById(R.id.main_hatchback);
        sedan = findViewById(R.id.main_sedan);
        suv = findViewById(R.id.main_suv);
        coupe = findViewById(R.id.main_coupe);
        minivan = findViewById(R.id.main_minivan);
        other = findViewById(R.id.main_other);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });

        setupButtonListeners();
    }

    private void setupRecyclerView() {
        rvCarListings = findViewById(R.id.rvCarListings);
        CarDatabaseManager dbManager = CarDatabaseManager.getInstance();
        List<CarListing> carListings = dbManager.getAllListings();
        carAdapter = CarAdapter.getInstance(this, this);
        carAdapter.updateData(carListings);
        rvCarListings.setAdapter(carAdapter);
        rvCarListings.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupButtonListeners() {
        ivNavigationButton.setOnClickListener(v -> navigateToProfile());
        homeButton.setOnClickListener(v -> showToast("Home clicked"));
        plusButton.setOnClickListener(v -> navigateToCreateListing());
        watchlistButton.setOnClickListener(v -> navigateToWatchList());
        searchEditText.setOnClickListener(v -> navigateToSearchActivity());
        hatchback.setOnClickListener(v -> onHatchbackClicked());
        sedan.setOnClickListener(v -> onSedanClicked());
        suv.setOnClickListener(v -> onSuvClicked());
        coupe.setOnClickListener(v -> onCoupeClicked());
        minivan.setOnClickListener(v -> onMinivanClicked());
        other.setOnClickListener(v -> onOtherClicked());
    }

    private void navigateToProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    private void navigateToCreateListing() {
        Intent intent = new Intent(this, CreateListingActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    private void navigateToWatchList() {
        Intent intent = new Intent(this, WatchListActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddWatchClick(int position) {
        CarListing carListing = carAdapter.getItem(position);
        if (carListing != null) {
            UserSession userSession = UserSession.getInstance(this);
            User user = userSession.getUser();
            WatchlistHelper.getInstance().addToWatchlist(user, carListing);
            Toast.makeText(this, "Added to watchlist: " + carListing.getCar().getModel(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error adding to Watchlist", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToSearchActivity(){
        Intent intent = new Intent(MainActivity.this, SearchResultsActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    private void onHatchbackClicked(){
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra("searchQuery", "Hatchback");
        startActivity(intent);
    }

    private void onSedanClicked(){
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra("searchQuery", "Sedan");
        startActivity(intent);
    }

    private void onSuvClicked(){
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra("searchQuery", "Suv");
        startActivity(intent);
    }

    private void onCoupeClicked(){
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra("searchQuery", "Coupe");
        startActivity(intent);
    }

    private void onMinivanClicked(){
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra("searchQuery", "Minivan");
        startActivity(intent);
    }
    private void onOtherClicked(){
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra("searchQuery", "Other");
        startActivity(intent);
    }

    private void initializeDemoData() {

        TimeZone timeZone = TimeZone.getDefault();
        String timeZoneID = timeZone.getID();
        ZonedDateTime currentDateTimeWithZone = ZonedDateTime.now(ZoneId.of(timeZoneID));

        ArrayList<Uri> listing1img = new ArrayList<>();
        Uri imageUri = ImageToDataURI.drawableToUri(this, R.drawable.bmw_back);
        Uri imageUri2 = ImageToDataURI.drawableToUri(this, R.drawable.bmw_front);
        listing1img.add(imageUri);
        listing1img.add(imageUri2);

        User DevenT1 = new User("1", "DevenT1", "Password145!", "DevenT1@example.com");
        Car car1 = new Car(DevenT1, "BMW", "M4 Competition", 2023, 2500, "sedan");
        CarListing listing1 = new CarListing("1", car1, 150000, currentDateTimeWithZone, listing1img);
        dbManager = CarDatabaseManager.getInstance();
        dbManager.addListing(DevenT1, listing1);

        ArrayList<Uri> listing2img = new ArrayList<>();
        Uri imageUriX5 = ImageToDataURI.drawableToUri(this, R.drawable.x5_front);
        Uri imageUriX52 = ImageToDataURI.drawableToUri(this, R.drawable.x5_back);
        listing2img.add(imageUriX5);
        listing2img.add(imageUriX52);

        User DevenT2 = new User("2", "DevenT2", "Password145!", "DevenT2@example.com");
        Car car2 = new Car(DevenT2, "BMW", "X5", 2012, 120000, "suv");
        CarListing listing2 = new CarListing("1", car2, 12000, currentDateTimeWithZone, listing2img);
        dbManager = CarDatabaseManager.getInstance();
        dbManager.addListing(DevenT2, listing2);

        ArrayList<Uri> listing3img = new ArrayList<>();
        Uri imageAccordOld = ImageToDataURI.drawableToUri(this, R.drawable.honda_accord_old_front_real);
        Uri imageAccordOldBack = ImageToDataURI.drawableToUri(this, R.drawable.honda_accord_old_front);
        listing3img.add(imageAccordOld);
        listing3img.add(imageAccordOldBack);

        User DevenT3 = new User("3", "DevenT3", "Password145!", "DevenT3@example.com");
        Car car3 = new Car(DevenT3, "Honda", "Accord", 2002, 154000, "sedan");
        CarListing listing3 = new CarListing("3", car3, 15500, currentDateTimeWithZone, listing3img);
        dbManager = CarDatabaseManager.getInstance();
        dbManager.addListing(DevenT3, listing3);

    }
}
