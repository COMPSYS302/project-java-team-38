package com.example.allgoods;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
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
import android.os.Handler;
import android.os.Looper;

import com.google.android.material.snackbar.Snackbar;

public class CreateListingActivity extends AppCompatActivity {

    private Spinner carTypeSpinner;
    private final String fieldsNotFilled = "Code 404";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

        initializeViews();
    }

    private void initializeViews() {
        ImageView backCreateListing = findViewById(R.id.backCreateListing);
        TextView confirmButton = findViewById(R.id.confirmButton);
        TextView generateDescriptionGpt = findViewById(R.id.generateDiscriptionGpt);
        Spinner carTypeSpinner = findViewById(R.id.carTypeSpinner);
        TextView validateListing = findViewById(R.id.validateListing);

        backCreateListing.setOnClickListener(v -> onBack());
        confirmButton.setOnClickListener(v -> showToast("Listing confirmed!"));
        validateListing.setOnClickListener(v -> validateListing());
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

}
