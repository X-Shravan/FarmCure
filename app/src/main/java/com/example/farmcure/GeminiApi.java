package com.example.farmcure;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GeminiApi {
    @Headers("Content-Type: application/json")
    @POST("v1beta/models/gemini-2.0-flash:generateContent")
    Call<com.example.farmcure.model.GeminiResponse> generateContent(
            @Header("X-Goog-Api-Key") String apiKey,
            @Body com.example.farmcure.model.GeminiRequest body
    );
}
