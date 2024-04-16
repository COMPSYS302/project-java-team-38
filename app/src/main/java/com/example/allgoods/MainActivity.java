package com.example.allgoods;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

    private boolean showAll = false;
    private ImageButton homeButton, plusButton, watchlistButton;

    private Button hatchback, sedan, suv, coupe, minivan, other;

    private ImageView ivNavigationButton;
    private LinearLayout categoriesLayout;
    private EditText searchEditText;
    private RecyclerView rvCarListings;
    private CarAdapter carAdapter;

    TextView mainViewAll;

    CarDatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DemoDataInitializer demoDataInitializer = DemoDataInitializer.getInstance(this);
        if(!demoDataInitializer.initializeDemoDataIfNecessary()) {
            initializeDemoDataInBackground();

    }
        try{
            updateListingsBasedOnViews();
        }catch(Exception e){
            showToast("Please Wait");
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

    @Override
    protected void onResume() {
        super.onResume();
        try{
            updateListingsBasedOnViews();
        }catch(Exception e){
            showToast("Please Wait");
        }
    }

    private void updateListingsBasedOnViews() {
        // Fetch the latest listings perhaps from a database or a service
        List<CarListing> carListings = dbManager.getAllListings();
        carAdapter.updateData(carListings);
        carAdapter.updateWeightedList();
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
        mainViewAll = findViewById(R.id.main_view_all);
        mainViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAll = !showAll;
                toggleShowAll();
                animateButton();
                if (showAll) {
                    animateListingViews();
                }
            }
        });



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

    @Override
    public void onImageClick(int position) {
        carAdapter.updateRecentlyViewed(position);
        CarListing carListing = carAdapter.getItem(position);
        navigateToCarDetails(carListing);
    }

    @Override
    public void onPriceClick(int position) {
        carAdapter.updateRecentlyViewed(position);
        CarListing carListing = carAdapter.getItem(position);
        navigateToCarDetails(carListing);
    }

    @Override
    public void onMakeModelClick(int position) {
        carAdapter.updateRecentlyViewed(position);
        CarListing carListing = carAdapter.getItem(position);
        navigateToCarDetails(carListing);
    }

    @Override
    public void onYearClick(int position) {
        carAdapter.updateRecentlyViewed(position);
        CarListing carListing = carAdapter.getItem(position);
        navigateToCarDetails(carListing);
    }

    @Override
    public void onOdoClick(int position) {
        carAdapter.updateRecentlyViewed(position);
        CarListing carListing = carAdapter.getItem(position);
        navigateToCarDetails(carListing);
    }

    private void navigateToCarDetails(CarListing carListing) {
        Intent intent = new Intent(MainActivity.this, IndepthListingActivity.class);
        intent.putExtra("CarListing", carListing); // Passing the entire object
        startActivity(intent);
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

    private void toggleShowAll() {
        if (carAdapter != null) {
            carAdapter.setShowAll(showAll);
            showAll = !showAll;
            carAdapter.setShowAll(showAll);  // Tell the adapter to switch mode
            carAdapter.notifyDataSetChanged();  // Ensure the RecyclerView updates
            animateButton();  // Handle any UI animation for toggling
        }
    }

    private void animateButton() {
        // Add animation to the button (example: fade in/out)
        Animation fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
        Animation fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);

        if (showAll) {
            mainViewAll.startAnimation(fadeOut);
        } else {
            mainViewAll.startAnimation(fadeIn);
        }
    }

    private void animateListings(List<View> listingViews) {
        // Delay between animations
        final int delayMillis = 200;

        // Initialize delay
        int delay = 0;

        // Iterate over listing views
        for (final View listingView : listingViews) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Apply animation
                    Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.waterfall);
                    listingView.startAnimation(animation);
                }
            }, delay);

            // Increment delay for next item
            delay += delayMillis;
        }
    }

    // Renamed method for animating individual listing views
    private void animateListingViews() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) rvCarListings.getLayoutManager();
        int itemCount = layoutManager.getItemCount();
        List<View> listings = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            View listingView = layoutManager.findViewByPosition(i);
            if (listingView != null) {
                // Add only non-null views to the list
                listings.add(listingView);
                // Apply animation to all listing views
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.waterfall);
                listingView.startAnimation(animation);
            }
        }

        // Now call animateListings with the list of visible listing views
        animateListings(listings);
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
        Car car1 = new Car(DevenT1, "BMW", "M4 Competition", 2023, 2500, "sedan", "HYH234");
        CarListing listing1 = new CarListing("1", car1, 150000, currentDateTimeWithZone, listing1img, "This is a M4" +
                " Competition with a really fast 0-100km. It is perfect for you to drive away and enjoy the thrill of speed" +
                " you will not find a car like this! Enquire for a test drive or if you cannot wait come and buy now!");
        dbManager = CarDatabaseManager.getInstance();
        dbManager.addListing(DevenT1, listing1);

        ArrayList<Uri> listing2img = new ArrayList<>();
        Uri imageUriX5 = ImageToDataURI.drawableToUri(this, R.drawable.x5_front);
        Uri imageUriX52 = ImageToDataURI.drawableToUri(this, R.drawable.x5_back);
        listing2img.add(imageUriX5);
        listing2img.add(imageUriX52);

        User DevenT2 = new User("2", "DevenT2", "Password145!", "DevenT2@example.com");
        Car car2 = new Car(DevenT2, "BMW", "X5", 2012, 120000, "suv", "YHF463");
        CarListing listing2 = new CarListing("1", car2, 12000, currentDateTimeWithZone, listing2img, "This is a X5" +
                "  with great space and comfort for your family. It is perfect for you to drive away and enjoy the thrill of adventure and the outdoors" +
                " you will not find a car like this! Enquire for a test drive or if you cannot wait come and buy now!");
        dbManager = CarDatabaseManager.getInstance();
        dbManager.addListing(DevenT2, listing2);

        ArrayList<Uri> listing3img = new ArrayList<>();
        Uri imageAccordOld = ImageToDataURI.drawableToUri(this, R.drawable.honda_accord_old_front_real);
        Uri imageAccordOldBack = ImageToDataURI.drawableToUri(this, R.drawable.honda_accord_old_front);
        listing3img.add(imageAccordOld);
        listing3img.add(imageAccordOldBack);

        User DevenT3 = new User("3", "DevenT3", "Password145!", "DevenT3@example.com");
        Car car3 = new Car(DevenT3, "Honda", "Accord", 2002, 154000, "sedan", "GDT825");
        CarListing listing3 = new CarListing("3", car3, 15500, currentDateTimeWithZone, listing3img, "This 2002 Honda " +
                " Accord offers reliable performance and a comfortable ride, even with 154,000 miles on the clock. Ideal for daily commuting " +
                " or a cost-effective option for new drivers. Contact us to schedule a test drive or to discuss purchase options!");

        dbManager = CarDatabaseManager.getInstance();
        dbManager.addListing(DevenT3, listing3);

        ArrayList<Uri> listing4img = new ArrayList<>();
        Uri imageKia = ImageToDataURI.drawableToUri(this, R.drawable.kia_front);
        Uri imageKiaBack = ImageToDataURI.drawableToUri(this, R.drawable.kia_back);
        listing4img.add(imageKia);
        listing4img.add(imageKiaBack);

        User DevenT4 = new User("4", "DevenT4", "Password145!", "DevenT4@example.com");
        Car car4 = new Car(DevenT4, "Kia", "Sportage", 2014, 170982, "suv", "SYR235");
        CarListing listing4 = new CarListing("4", car4, 11900, currentDateTimeWithZone, listing4img, "Explore the roads " +
                " with this Kia Sportage, perfect for both city driving and weekend getaways. With robust handling and " +
                " ample space, it's ideal for families or anyone needing a reliable SUV. " +
                " Available now for a test drive or immediate purchase!");
        dbManager = CarDatabaseManager.getInstance();
        dbManager.addListing(DevenT4, listing4);

        ArrayList<Uri> listing5img = new ArrayList<>();
        Uri imageLexus = ImageToDataURI.drawableToUri(this, R.drawable.lexus_f_is350);
        Uri imageLexusBack = ImageToDataURI.drawableToUri(this, R.drawable.lexus_back_f);
        listing5img.add(imageLexus);
        listing5img.add(imageLexusBack);

        User DevenT5 = new User("5", "DevenT5", "Password145!", "DevenT5@example.com");
        Car car5 = new Car(DevenT5, "Lexus", "IS350 F Sport", 2013, 56538, "Sedan", "POE823");
        CarListing listing5 = new CarListing("5", car5, 28950, currentDateTimeWithZone, listing5img,
                "Step into luxury with the Lexus IS350 F Sport, blending performance with premium comfort. " +
                        " Its aggressive styling and responsive handling make it a standout choice for discerning drivers." +
                        " Experience it firsthand with a test drive or take it home today!"
        );
        dbManager = CarDatabaseManager.getInstance();
        dbManager.addListing(DevenT5, listing5);

        ArrayList<Uri> listing6img = new ArrayList<>();
        Uri imageHondaAccord2Front = ImageToDataURI.drawableToUri(this, R.drawable.honda2_front);
        Uri imageHondaAccord2Back = ImageToDataURI.drawableToUri(this, R.drawable.honda2_front);
        listing6img.add(imageHondaAccord2Front);
        listing6img.add(imageHondaAccord2Back);


        ArrayList<Uri> listing7img = new ArrayList<>();
        Uri imageNissianCube = ImageToDataURI.drawableToUri(this, R.drawable.nissan_cube_front);
        Uri imageNissianCubeBack = ImageToDataURI.drawableToUri(this, R.drawable.nissan_cube_front);
        listing7img.add(imageNissianCube);
        listing7img.add(imageNissianCubeBack);

        User DevenT7 = new User("7", "DevenT7", "Password145!", "DevenT7@example.com");
        Car car7 = new Car(DevenT7, "Nissian", "Cube", 2012, 51000, "Hatchback", "GST826");
        CarListing listing7 = new CarListing("7", car7, 8488, currentDateTimeWithZone, listing7img,
                "Discover the unique charm of the Nissan Cube, an urban-friendly compact with" +
                        " distinctive styling and spacious interior. Ideal for city dwellers seeking practicality" +
                        " with personality. Ready for you to drive off in style or come in for a test drive today!");
        dbManager = CarDatabaseManager.getInstance();
        dbManager.addListing(DevenT7, listing7);

        ArrayList<Uri> listing8img = new ArrayList<>();
        Uri imagePorsche = ImageToDataURI.drawableToUri(this, R.drawable.porsche_front);
        Uri imageporscheBack = ImageToDataURI.drawableToUri(this, R.drawable.porsche_back);
        listing8img.add(imagePorsche);
        listing8img.add(imageporscheBack);

        User DevenT8 = new User("8", "DevenT8", "Password145!", "DevenT8@example.com");
        Car car8 = new Car(DevenT8, "Porsche", "911 GT3 RS", 2016, 6050, "Coupe", "HSO827");
        CarListing listing8 = new CarListing("8", car8, 325000, currentDateTimeWithZone, listing8img,
                "Unleash the thrill of the Porsche 911 GT3 RS, a track-inspired powerhouse designed " +
                        " for speed enthusiasts. Its razor-sharp handling and explosive acceleration offer an " +
                        " exhilarating driving experience. Available now for those ready to dominate the road or track.");
        dbManager = CarDatabaseManager.getInstance();
        dbManager.addListing(DevenT8, listing8);
    }
}
