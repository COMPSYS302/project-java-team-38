package com.example.allgoods;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OpenAIApiService {
    @POST("chat/completions")
    Call<Map<String, Object>> createCompletion(@Header("Authorization") String authHeader, @Body Map<String, Object> requestBody);
}

