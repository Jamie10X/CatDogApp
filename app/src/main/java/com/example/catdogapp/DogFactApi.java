package com.example.catdogapp;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DogFactApi {
    @GET("api/v2/facts")
    Call<DogFactResponse> getRandomDogFact(); // E
}
