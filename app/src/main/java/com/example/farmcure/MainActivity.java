package com.example.farmcure;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button btnSelectImage, btnDiagnose;
    ImageView imagePreview;
    TextView textResult;
    Bitmap selectedBitmap;

    // Disease to Solution Mapping
    private final Map<String, String> diseaseSolutions = new HashMap<String, String>() {{
        put("Leaf spot", "Apply appropriate fungicide and avoid overhead watering.");
        put("Blight", "Remove infected plants and use resistant varieties.");
        put("Rust", "Use sulfur-based fungicides and rotate crops.");
        put("Powdery mildew", "Ensure good air circulation and apply neem oil.");
        put("Healthy", "No disease detected. Keep monitoring your crop.");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnDiagnose = findViewById(R.id.btnDiagnose);
        imagePreview = findViewById(R.id.imagePreview);
        textResult = findViewById(R.id.textResult);

        btnSelectImage.setOnClickListener(v -> {
            ImagePicker.with(this)
                    .crop()
                    .galleryOnly()
                    .start();
        });

        btnDiagnose.setOnClickListener(v -> {
            if (selectedBitmap != null) {
                diagnoseWithMLKit(selectedBitmap);
            } else {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                imagePreview.setImageBitmap(selectedBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void diagnoseWithMLKit(Bitmap bitmap) {
        InputImage image = InputImage.fromBitmap(bitmap, 0);
        ImageLabeler labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);

        labeler.process(image)
                .addOnSuccessListener(labels -> {
                    StringBuilder resultText = new StringBuilder("Diagnosis Result:\n");
                    for (ImageLabel label : labels) {
                        String disease = label.getText();
                        float confidence = label.getConfidence() * 100;
                        resultText.append(disease)
                                .append(" - Confidence: ")
                                .append(String.format("%.2f", confidence))
                                .append("%\n");

                        // Call the Gemini API for disease diagnosis
                        diagnoseWithGeminiAPI(disease);
                    }
                    textResult.setText(resultText.toString());
                })
                .addOnFailureListener(e -> textResult.setText("Error: " + e.getMessage()));
    }

    private void diagnoseWithGeminiAPI(String diseaseName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://generativelanguage.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GeminiApi api = retrofit.create(GeminiApi.class);

        // Prepare the request body for Gemini API
        com.example.farmcure.model.GeminiRequest prompt = new com.example.farmcure.model.GeminiRequest("What is the solution for " + diseaseName + " in crops?");
        Call<com.example.farmcure.model.GeminiResponse> call = api.generateContent("Your api key", prompt);

        call.enqueue(new Callback<com.example.farmcure.model.GeminiResponse>() {
            @Override
            public void onResponse(Call<com.example.farmcure.model.GeminiResponse> call, Response<com.example.farmcure.model.GeminiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().candidates != null && !response.body().candidates.isEmpty()) {
                        String solution = response.body().candidates.get(0).content.parts.get(0).text;
                        textResult.setText("Solution:\n" + solution);
                    } else {
                        textResult.setText("No solution returned.");
                    }
                } else {
                    textResult.setText("Failed to get solution. Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<com.example.farmcure.model.GeminiResponse> call, Throwable t) {
                t.printStackTrace();
                textResult.setText("Gemini API Error: " + t.getMessage());
            }
        });
    }
}
