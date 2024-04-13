package com.example.allgoods;

import androidx.annotation.Nullable;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;
import org.json.JSONArray;
import org.json.JSONObject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.os.Handler;
import android.os.Looper;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import java.util.UUID;

import com.google.android.material.snackbar.Snackbar;

public class CreateListingActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private ImageSliderAdapter adapter;
    private static final int PICK_IMAGE_REQUEST = 1; // Request code for picking an image
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2;

    private Spinner carTypeSpinner;
    private ArrayList<Uri> images;

    private final String fieldsNotFilled = "Code 404";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);
        images = new ArrayList<>();
        initializeViews();
        setupViewPager();
        setupPhotoUploadFeature();
    }

    private void initializeViews() {
        ImageView backCreateListing = findViewById(R.id.backCreateListing);
        TextView confirmButton = findViewById(R.id.confirmButton);
        TextView generateDescriptionGpt = findViewById(R.id.generateDiscriptionGpt);
        Spinner carTypeSpinner = findViewById(R.id.carTypeSpinner);
        TextView validateListing = findViewById(R.id.validateListing);
        ImageButton addPhotoButton = findViewById(R.id.addPhotoButton);


        backCreateListing.setOnClickListener(v -> onBack());
        confirmButton.setOnClickListener(v -> onConfirmListing());
        validateListing.setOnClickListener(v -> validateListing());
        addPhotoButton.setOnClickListener(v -> openGallery());
        generateDescriptionGpt.setOnClickListener(v -> {
            String prompt = buildPrompt();
            if(prompt.equals(fieldsNotFilled)) {
                showToast("Please fill in all fields");
            }else {
                generateDescription(prompt);
            }
        });
        // Initialize the carTypeSpinner and set up with ArrayAdapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.car_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carTypeSpinner.setAdapter(adapter);
    }

    private String getSelectedCarType() {
        return carTypeSpinner.getSelectedItem().toString();
    }

    private void generateDescription(String userPrompt) {
        OkHttpClient client = new OkHttpClient();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("model", "gpt-3.5-turbo");
            JSONArray messages = new JSONArray();
            JSONObject messageContent = new JSONObject();
            messageContent.put("role", "user");
            messageContent.put("content", userPrompt);
            messages.put(messageContent);
            jsonObject.put("messages", messages);
            jsonObject.put("temperature", 0.7);

        } catch (Exception e) {
            showToast("Failed to create JSON payload");
        }

        RequestBody body = RequestBody.create(jsonObject.toString(), JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions") // Corrected URL
                .addHeader("Authorization", "Bearer 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") // Use your actual API key
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> showToast("Network error: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseData);
                        JSONArray choices = jsonResponse.getJSONArray("choices");
                        if (choices.length() > 0) {
                            JSONObject firstChoice = choices.getJSONObject(0);
                            JSONObject message = firstChoice.getJSONObject("message");
                            String content = message.getString("content");

                            // Animate typing the content into the EditText
                            runOnUiThread(() -> animateTypingText(content));
                        }
                    } catch (Exception e) {
                        runOnUiThread(() -> showToast("Failed to parse response: " + e.getMessage()));
                    }
                } else {
                    runOnUiThread(() -> showToast("Request failed: " + response.code() + ", " + response.message()));
                }
            }
        });
    }

    private void animateTypingText(String text) {
        EditText carDescriptionEditText = findViewById(R.id.carDescription);
        carDescriptionEditText.setText(""); // Clear previous text

        final Handler handler = new Handler(Looper.getMainLooper());
        final int delay = 50; // Milliseconds delay between characters

        Runnable typingRunnable = new Runnable() {
            int index = 0;

            @Override
            public void run() {
                if (index < text.length()) {
                    carDescriptionEditText.append(String.valueOf(text.charAt(index)));
                    index++;
                    handler.postDelayed(this, delay);
                }
            }
        };

        handler.post(typingRunnable);
    }

    private void showToast(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();

    }

    private String buildPrompt() {
        EditText carMakeEditText = findViewById(R.id.CarMake);
        EditText carModelEditText = findViewById(R.id.Model);
        EditText carYearEditText = findViewById(R.id.carYearEditText);
        EditText carMileageEditText = findViewById(R.id.Milage);

        if(carMakeEditText.getText().toString().isEmpty() || carModelEditText.getText().toString().isEmpty() ||
                carYearEditText.getText().toString().isEmpty() || carMileageEditText.getText().toString().isEmpty()){
            return "Code 404";
        }

        return "Generate a car listing description for a " +
                carYearEditText.getText().toString() + " " +
                carMakeEditText.getText().toString() + " " +
                carModelEditText.getText().toString() +
                " with " + carMileageEditText.getText().toString() + " miles.";
    }

    private void onBack(){
        finish();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }

    private void validateListing(){
        OkHttpClient client = new OkHttpClient();

        // Retrieve values from EditText fields
        EditText numberPlateEditText = findViewById(R.id.NumberPlate);
        EditText mileageEditText = findViewById(R.id.Milage); // Make sure you have this EditText in your layout

        // Get the text values from the EditTexts
        String numberPlate = numberPlateEditText.getText().toString().trim().toUpperCase();
        String mileage = mileageEditText.getText().toString().trim();

        // Validate that the inputs are not empty
        if(numberPlate.isEmpty() || mileage.isEmpty()) {
            showToast("Please fill in all required fields");
            return;
        }

        // Dynamically construct the URL with the input values
        String url = "https://api.bestcar.co.nz/api/vehicle?request=residual_value_extended&accesskey=2EAVcz0nXJETK6kazPBGVnj6SeI3is&numberplate=" + numberPlate + "&odometer=" + mileage;

        // Create a request object
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Enqueue the request to be executed asynchronously
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle failure cases, like network errors
                runOnUiThread(() -> showToast("Failed to fetch data: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Parse the response to extract the needed data
                    String responseData = response.body().string();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseData);
                        JSONObject vehicle = jsonResponse.getJSONObject("vehicle");
                        JSONObject residualValue = vehicle.getJSONObject("residualValue");
                        int averagePrice = residualValue.getInt("avg");

                        // Update the EditText with the average price on the UI thread
                        EditText carPriceEditText = findViewById(R.id.carPrice);
                        carPriceEditText.setText(String.valueOf(averagePrice));

                    } catch (Exception e) {
                        runOnUiThread(() -> showToast("Failed to parse the response: " + e.getMessage()));
                    }
                } else {
                    // Handle response failure cases
                    runOnUiThread(() -> showToast("Request failed: " + response.code() + ", " + response.message()));
                }
            }
        });
    }

    private void setupViewPager() {
        viewPager2 = findViewById(R.id.addPhotoCreateListing);
        adapter = new ImageSliderAdapter(this);
        viewPager2.setAdapter(adapter);

        // Optionally, add a page change callback for swipe actions
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.d("ViewPager", "Selected page position: " + position);
            }
        });
    }


    private void setupPhotoUploadFeature() {
        // Now viewPager2 should not be null
        viewPager2.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            } else {
                openGallery();
            }
        });
    }
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            ArrayList<Uri> imageUris = new ArrayList<>(adapter.getImageUris()); // Get current images
            images.addAll(imageUris);
            imageUris.add(imageUri); // Add new image
            adapter.setImageUris(imageUris); // Update adapter with new list of images
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                // Permission was denied. You can show a message to the user or disable the feature.
                showToast("Permission denied to read your External storage");
            }
        }
    }

    public void onConfirmListing(){
        EditText carMakeEditText = findViewById(R.id.CarMake);
        EditText carModelEditText = findViewById(R.id.Model);
        EditText carYearEditText = findViewById(R.id.carYearEditText);
        EditText carMileageEditText = findViewById(R.id.Milage);
        EditText carPriceEditText = findViewById(R.id.carPrice);
        String carMake = carMakeEditText.getText().toString();
        String carModel = carModelEditText.getText().toString();
        int carYear = Integer.parseInt(carYearEditText.getText().toString());
        int carMileage = Integer.parseInt(carMileageEditText.getText().toString());
        Integer carPrice = Integer.parseInt(carPriceEditText.getText().toString());
        UserSession user = UserSession.getInstance(this);
        User userInSession = user.getUser();
        TimeZone timeZone = TimeZone.getDefault();
        String timeZoneID = timeZone.getID();
        ZonedDateTime currentDateTimeWithZone = ZonedDateTime.now(ZoneId.of(timeZoneID));
        String uniqueKey = UniqueIdGenerator.generateUniqueId();
        Car userCar = new Car(userInSession,carMake,carModel,carYear,carMileage);
        CarListing userCarListing = new CarListing(uniqueKey,userCar,carPrice,currentDateTimeWithZone,images);
        CarDatabaseManager carListingAdder = CarDatabaseManager.getInstance();
        carListingAdder.addListing(userInSession,userCarListing);
        Intent intent = new Intent(CreateListingActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }


}
