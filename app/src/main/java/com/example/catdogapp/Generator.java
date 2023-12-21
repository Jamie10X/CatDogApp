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

        // Finding and setting up the 'generate' and 'button2' buttons from the layout

        Button button2 = findViewById(R.id.button2);
        Button generate = findViewById(R.id.button);

        // OnClickListener for 'generate' button to navigate back to MainActivity

        generate.setOnClickListener(v -> {
            Intent intent = new Intent(Generator.this, MainActivity.class);
            startActivity(intent);
        });


        // OnClickListener for 'button2' to navigate to MultipleChoiceActivity with the animal type

        button2.setOnClickListener(v -> {
            Intent intent = new Intent(Generator.this, multiplechoice.class);
            intent.putExtra("animalType", animalType );
            startActivity(intent);
        });

        // Retrieving the 'animalType' passed from the previous activity

        animalType = getIntent().getStringExtra("animalType");


        // Setting up the ImageView and TextView for displaying the animal image and fact

        ImageView animalImage = findViewById(R.id.imageView);
        TextView factTextView = findViewById(R.id.textFact);

        // Checking the animal type and loading the appropriate image and fact

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


    // Methods to load cat and dog images from their respective APIs using Retrofit and Picasso

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


    // Methods to load cat and dog images from their respective APIs using Retrofit and Picasso


    private void loadCatFact(final TextView factTextView) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cat-fact.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CatFactApi service = retrofit.create(CatFactApi.class);
        Call<CatFact> call = service.getCatFact();

        call.enqueue(new Callback<CatFact>() {
            @Override
            public void onResponse(@NonNull Call<CatFact> call, @NonNull Response<CatFact> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String fact = response.body().getCatFact();
                    if (fact.length() < 8) {
                        factTextView.setText(R.string.catfactshort);
                    } else if (isValidFact(fact)) {
                        fact = truncateFact(fact); // Truncate the fact if it's too long
                        factTextView.setText(fact);
                    } else {
                        factTextView.setText(R.string.catfactlong);
                    }
                } else {
                    factTextView.setText(R.string.failed_to_load_fact);
                }
        }

            @Override
            public void onFailure(@NonNull Call<CatFact> call, @NonNull Throwable t) {
                factTextView.setText(R.string.error_fetching_fact);
            }
        });
    }

    // Methods to load cat and dog images from their respective APIs using Retrofit and Picasso

    private boolean isValidFact(String fact) {
        return fact.matches("[A-Za-z0-9 .,?!'-]*");
    }

    // Utility method to truncate a fact if it exceeds a specified length

    private String truncateFact(String fact) {
        return fact.length() > 100 ? fact.substring(0, 100) + "..." : fact;
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
            public void onResponse(@NonNull Call<DogFactResponse> call, @NonNull Response<DogFactResponse> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().getData().isEmpty()) {
                    DogFactResponse.DogFact dogFact = response.body().getData().get(0);
                    factTextView.setText(dogFact.getFact());
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<DogFactResponse> call, @NonNull Throwable t) {
                factTextView.setText("Error fetching fact");
            }
        });
    }
}