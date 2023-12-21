package com.example.catdogapp;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CatFactApi {
    @GET("/facts/random") // Adjust the endpoint as per API documentation
    Call<CatFact> getCatFact();
}
