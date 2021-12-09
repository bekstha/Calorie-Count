package fi.metropolia.practisecalorie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import fi.metropolia.practisecalorie.user.LoggedUser;
import fi.metropolia.practisecalorie.user.User;
import fi.metropolia.practisecalorie.user.UserDao;
import fi.metropolia.practisecalorie.user.UserDatabase;

public class CalorieRequirement extends AppCompatActivity {

    TextView tvCalorieRequirement;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_requirement);

        Intent intent = getIntent();
        int calorieRequirement = intent.getIntExtra(Profile.EXTRA_CALORIE_REQUIREMENT,0);
        String userName =intent.getStringExtra(Profile.EXTRA_USERNAME);
        String password = intent.getStringExtra(Profile.EXTRA_PASSWORD);

        tvCalorieRequirement = findViewById(R.id.tvCalorieRequirement);
        tvCalorieRequirement.setText("Your daily calorie requirement is " + calorieRequirement + " Kcal");

        Button startBtn = findViewById(R.id.startBtn);
        startBtn.setOnClickListener(v -> {

            UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
            final UserDao userDao = userDatabase.userDao();
            User user = userDao.login(userName,password);

            LoggedUser loggedUser  = LoggedUser.getInstance();
            loggedUser.setUserID(user.getId());

            Intent intent1 = new Intent(CalorieRequirement.this, Overview.class);
            startActivity(intent1);
        });
    }
}

