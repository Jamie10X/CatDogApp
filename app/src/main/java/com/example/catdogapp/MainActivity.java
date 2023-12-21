package com.example.catdogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    // This method is triggered when the 'cat' button is clicked

    public void catClicked(View v){
        // Displays a toast message indicating that a cat image is being loaded
        Toast.makeText(this,"Cat image ",
                Toast.LENGTH_LONG).show();
        // Creates an intent to navigate to the Generator activity

        Intent intent = new Intent(MainActivity.this, Generator.class);

        // Puts extra information in the intent specifying that the animal type is 'cat'

        intent.putExtra("animalType", "cat");
        startActivity(intent);
    }

    public void dogClicked(View v){
        Toast.makeText(this,"Dog image",
                Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, Generator.class);
        intent.putExtra("animalType", "dog");
        startActivity(intent);
    }
}