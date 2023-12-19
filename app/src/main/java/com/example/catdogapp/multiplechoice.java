package com.example.catdogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.Button;
import android.widget.RadioButton;

import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class multiplechoice extends AppCompatActivity {



    private TextView questionTextView;
    private RadioButton optionA, optionB, optionC;
    private RadioGroup optionsRadioGroup;
    private JSONArray catTrivia, dogTrivia;

   private String animalType;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiplechoice);

        animalType = getIntent().getStringExtra("animalType");


        questionTextView = findViewById(R.id.questionTextView);
        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        optionC = findViewById(R.id.optionC);
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        Button submitButton = findViewById(R.id.submit);

        loadTriviaQuestions();
        displayNextQuestion(animalType);

        submitButton.setOnClickListener(v -> {
            checkAnswer();
            displayNextQuestion(animalType);
        });
    }

    private void loadTriviaQuestions() {
        try {
            String fileName = this.animalType.equals("cat") ? "catquest.json" : "dogquest.json";
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);
            JSONObject obj = new JSONObject(json);
            if (this.animalType.equals("cat")) {
                catTrivia = obj.getJSONArray("catTrivia");
            } else {
                dogTrivia = obj.getJSONArray("dogTrivia");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayNextQuestion(String animalType) {
        try {
            JSONArray triviaArray = animalType.equals("cat") ? catTrivia : dogTrivia;
            int randomQuestionIndex = new Random().nextInt(triviaArray.length());
            JSONObject questionObj = triviaArray.getJSONObject(randomQuestionIndex);
            questionTextView.setText(questionObj.getString("question"));
            JSONArray options = questionObj.getJSONArray("options");
            optionA.setText(String.format("A. %s", options.getString(0)));
            optionB.setText(String.format("B. %s", options.getString(1)));
            optionC.setText(String.format("C. %s", options.getString(2)));
            optionsRadioGroup.clearCheck();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkAnswer() {
        try {
            int selectedId = optionsRadioGroup.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = findViewById(selectedId);
            if (selectedRadioButton != null) {
                String selectedAnswer = selectedRadioButton.getText().toString();
                JSONArray triviaArray = animalType.equals("cat") ? catTrivia : dogTrivia;
                int randomQuestionIndex = new Random().nextInt(triviaArray.length());
                JSONObject questionObj = triviaArray.getJSONObject(randomQuestionIndex);
                String correctAnswer = questionObj.getString("answer");
                if (selectedAnswer.endsWith(correctAnswer)) {
                    Toast.makeText(this, "Correct Answer!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Incorrect Answer!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}