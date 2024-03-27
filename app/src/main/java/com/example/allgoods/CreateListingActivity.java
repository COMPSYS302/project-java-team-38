package com.example.allgoods;

import android.os.Bundle;
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

public class CreateListingActivity extends AppCompatActivity {

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

        backCreateListing.setOnClickListener(v -> finish());
        confirmButton.setOnClickListener(v -> showToast("Listing confirmed!"));

        generateDescriptionGpt.setOnClickListener(v -> {
            String prompt = buildPrompt();
            generateDescription(prompt);
        });
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
        runOnUiThread(() -> Toast.makeText(CreateListingActivity.this, message, Toast.LENGTH_SHORT).show());
    }

    private String buildPrompt() {
        EditText carMakeEditText = findViewById(R.id.CarMake);
        EditText carModelEditText = findViewById(R.id.Model);
        EditText carYearEditText = findViewById(R.id.carYearEditText);
        EditText carMileageEditText = findViewById(R.id.Milage);

        return "Generate a car listing description for a " +
                carYearEditText.getText().toString() + " " +
                carMakeEditText.getText().toString() + " " +
                carModelEditText.getText().toString() +
                " with " + carMileageEditText.getText().toString() + " miles.";
    }
}
