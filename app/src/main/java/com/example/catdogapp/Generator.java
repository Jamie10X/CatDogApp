package com.example.catdogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Generator extends AppCompatActivity {
    private String animalType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generator);

        Button button2 = findViewById(R.id.button2);
        Button generate = findViewById(R.id.button);

        generate.setOnClickListener(v -> {
            Intent intent = new Intent(Generator.this, MainActivity.class);
            startActivity(intent);
        });


        button2.setOnClickListener(v -> {
            Intent intent = new Intent(Generator.this, multiplechoice.class);
            intent.putExtra("animalType", animalType );
            startActivity(intent);
        });

        animalType = getIntent().getStringExtra("animalType");
        ImageView animalImage = findViewById(R.id.imageView);
        TextView factTextView = findViewById(R.id.textFact);

        if (animalType != null) {
            if (animalType.equals("cat")) {
                loadCatImage(animalImage);
                loadCatFact(factTextView);
            } else if (animalType.equals("dog")) {
                loadDogImage(animalImage);
                loadDogFact(factTextView);
            }
        }
    }

    private void loadCatImage(ImageView catImage) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thecatapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CatImageApi catImageApi = retrofit.create(CatImageApi.class);
        Call<List<CatImage>> call = catImageApi.getData();

        call.enqueue(new Callback<List<CatImage>>() {
            @Override
            public void onResponse(@NonNull Call<List<CatImage>> call, @NonNull Response<List<CatImage>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String imageUrl = response.body().get(0).getUrl();
                    Picasso.get().load(imageUrl).into(catImage);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CatImage>> call, @NonNull Throwable t) {
                System.out.println("technical issue! Try again");
            }
        });
    }

    private void loadDogImage(ImageView dogImage) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thedogapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DogImageApi dogImageApi = retrofit.create(DogImageApi.class);
        Call<List<DogImage>> call = dogImageApi.getData();

        call.enqueue(new Callback<List<DogImage>>() {
            @Override
            public void onResponse(@NonNull Call<List<DogImage>> call, @NonNull Response<List<DogImage>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String imageUrl = response.body().get(0).getUrl();
                    Picasso.get().load(imageUrl).into(dogImage);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<DogImage>> call, @NonNull Throwable t) {
                System.out.println("technical issue! Try again");
            }
        });
    }

    private void loadCatFact(final TextView factTextView) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cat-fact.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CatFactApi service = retrofit.create(CatFactApi.class);
        Call<CatFact> call = service.getCatFact();

        call.enqueue(new Callback<CatFact>() {
            @Override
            public void onResponse(Call<CatFact> call, Response<CatFact> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String fact = response.body().getCatFact();
                    if (isValidFact(fact)) {
                        fact = truncateFact(fact, 100); // Adjust the max length as needed
                        factTextView.setText(fact);
                    } else {
                        factTextView.setText(R.string.fact_not_suitable_for_display);
                    }
                } else {
                    factTextView.setText(R.string.failed_to_load_fact);
                }
            }

            @Override
            public void onFailure(Call<CatFact> call, Throwable t) {
                factTextView.setText(R.string.error_fetching_fact);
            }
        });
    }

    private boolean isValidFact(String fact) {
        return fact.matches("[A-Za-z0-9 .,?!'-]*");
    }

    private String truncateFact(String fact, int maxLength) {
        return fact.length() > maxLength ? fact.substring(0, maxLength) + "..." : fact;
    }


    private void loadDogFact(final TextView factTextView) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dogapi.dog/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DogFactApi service = retrofit.create(DogFactApi.class);
        Call<DogFactResponse> call = service.getRandomDogFact();

        call.enqueue(new Callback<DogFactResponse>() {
            @Override
            public void onResponse(Call<DogFactResponse> call, Response<DogFactResponse> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().getData().isEmpty()) {
                    DogFactResponse.DogFact dogFact = response.body().getData().get(0);
                    factTextView.setText(dogFact.getFact());
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(Call<DogFactResponse> call, Throwable t) {
                factTextView.setText("Error fetching fact");
            }
        });
    }
}