package fi.metropolia.practisecalorie;

import android.content.Intent;
import android.os.Bundle;
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
        //on completed view, checking if user has made any entry for the day and responding likewise
        if (sumOfCalorieConsumed == 0) {
            completedView.setText(getResources().getString(R.string.no_entry));
        }else if (sumOfCalorieConsumed < calorieRequirement){
            double loseWeightNum = calorieRequirement - sumOfCalorieConsumed;
                completedView.setText(getResources().getString(R.string.loose_weight,String.valueOf(sumOfCalorieConsumed),String.valueOf(loseWeightNum)));
            }else if (sumOfCalorieConsumed > calorieRequirement){
            double gainWeightNum = sumOfCalorieConsumed - calorieRequirement;
                completedView.setText(getResources().getString(R.string.gain_weight, String.valueOf(sumOfCalorieConsumed), String.valueOf(gainWeightNum)));
            } else {
                completedView.setText( getResources().getString(R.string.maintain_weight, String.valueOf(sumOfCalorieConsumed)));
            }

        endDayBtn = findViewById(R.id.endDayBtn);
        endDayBtn.setOnClickListener(v -> startActivity(new Intent(CompletedDay.this, MainActivity.class)));

    }
}