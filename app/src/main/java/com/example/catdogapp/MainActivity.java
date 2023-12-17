package com.example.catdogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton catImage = findViewById(R.id.catImage);
        ImageButton dogImage = findViewById(R.id.dogImage);

    }

    public void catClicked(View v){
        Toast.makeText(this,"Cat image ",
                Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, Generator.class);
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