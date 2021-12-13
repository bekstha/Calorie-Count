package fi.metropolia.practisecalorie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.Objects;

import fi.metropolia.practisecalorie.user.LoggedUser;
import fi.metropolia.practisecalorie.user.UserDatabase;

public class CompletedDay extends AppCompatActivity {
    TextView completedView;
    Button endDayBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_day);
        Objects.requireNonNull(getSupportActionBar()).hide();

        UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
        double calorieRequirement = userDatabase.userDao().searchCalorieRequirement(LoggedUser.getUserID());
        double sumOfCalorieConsumed = userDatabase.foodDAO().getTotal(LocalDate.now(), LoggedUser.getUserID());

        completedView = findViewById(R.id.completedView);

        if (sumOfCalorieConsumed < calorieRequirement){
            completedView.setText("Your calorie intake for the day was " + sumOfCalorieConsumed + " Kcal which is " + (calorieRequirement - sumOfCalorieConsumed) + " Kcal lower than recommended. If you continue like this, you will be losing weight.");
        }else if (sumOfCalorieConsumed > calorieRequirement){
            completedView.setText("Your calorie intake for the day was " + sumOfCalorieConsumed + " Kcal which is " + (sumOfCalorieConsumed - calorieRequirement) + " Kcal higher than recommended. If you continue like this, you will be gaining weight.");
        } else {
            completedView.setText("Your calorie intake for the day was " + sumOfCalorieConsumed + " Kcal. You are on course to maintain your current weight.");
        }

        endDayBtn = findViewById(R.id.endDayBtn);
        endDayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CompletedDay.this, MainActivity.class));
            }
        });



    }
}