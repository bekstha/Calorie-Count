package fi.metropolia.practisecalorie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class CalorieRequirement extends AppCompatActivity {

    TextView tvCalorieRequirement;
    int  weight, height, age;

    String gender;
    int calorieRequirement;




    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_requirement);

        Intent intent = getIntent();
        height = intent.getIntExtra("height", 0);
        weight = intent.getIntExtra("weight", 0);
        age = intent.getIntExtra("age", 0);
        gender = intent.getStringExtra("userGender");

        //checking the gender as calorie requirement depends on it and continuing with the calculation
        if (gender.equals("Male")){
            calorieRequirement = (int) Math.ceil((1.2*(66 + (6.3 * (weight * 2.20462)) + (12.9 * (height * 0.393701)) - (6.8 * age))));

        }else{
            calorieRequirement = (int) Math.ceil((1.2*(655 + (4.3 * (weight * 2.20462) + (4.7 * (height *0.393701)) -(4.7 * age)))));
        }


        tvCalorieRequirement = findViewById(R.id.tvCalorieRequirement);
        tvCalorieRequirement.setText("Your daily calorie requirement is " + calorieRequirement + " Kcal");


        Button startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(v -> {
            Intent intent1 = new Intent(CalorieRequirement.this, Overview.class);
            startActivity(intent1);
        });
    }
}

