package fi.metropolia.practisecalorie;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import fi.metropolia.practisecalorie.data.FoodViewModel;
import fi.metropolia.practisecalorie.user.LoggedUser;
import fi.metropolia.practisecalorie.user.UserDatabase;

public class History extends AppCompatActivity {


    TextView tvCalendar, tvTotalForDay;

    DatePickerDialog.OnDateSetListener dateSetListener;
    String chosenDate, todayDate;
    private FoodViewModel foodViewModel;
    double totalForTheDay;

    //For the bottom navigation View
    BottomNavigationView bottomNavigationHistory;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //To get the bottomNavigation view by Id
        bottomNavigationHistory = findViewById(R.id.bottomNavigationHistory);

        //Set HISTORY selected
        bottomNavigationHistory.setSelectedItemId(R.id.history);

        tvCalendar = findViewById(R.id.tvCalendar);
        tvTotalForDay = findViewById(R.id.numTotalForDay);

        RecyclerView historyRecyclerView = findViewById(R.id.historyRecycleView);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        todayDate = simpleDateFormat.format(Calendar.getInstance().getTime());

        tvCalendar.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),dateSetListener,year,month,day);
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
            datePickerDialog.show();

        });

        dateSetListener = (view, year1, month1, dayOfMonth) -> {
            // month starts from 0 in java so need to add 1 to it
            month1 = month1 +1;
            chosenDate = dayOfMonth + "/" + month1 + "/" + year1;
            LocalDate selectedDay = LocalDate.of(year1, month1, dayOfMonth);
            tvCalendar.setText(chosenDate);

            FoodAdapter adapter =new FoodAdapter();
            historyRecyclerView.setAdapter(adapter);
            foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
            foodViewModel.getFoodsByDate(selectedDay).observe(this, adapter::setFoods);
            UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
            totalForTheDay = userDatabase.foodDAO().getTotal(selectedDay, LoggedUser.getUserID());
            tvTotalForDay.setText(String.valueOf(totalForTheDay));
        };


        bottomNavigationHistory.setOnItemSelectedListener(item -> {
            //To click on Profile, the profile activity will open
            if (item.getItemId() == R.id.profile) {
                startActivity(new Intent(getApplicationContext(),
                        EditProfile.class));
                overridePendingTransition(0, 0);

                // To Click on home, it will stay in overview activity.
            } else if (item.getItemId() == R.id.home) {
                startActivity(new Intent(getApplicationContext(),
                        Overview.class));
                overridePendingTransition(0, 0);

                //To click on history it will go to history activity
            } else {
                startActivity(new Intent(getApplicationContext(),
                        History.class));
                overridePendingTransition(0, 0);

            }
            return true;
        });

    }
}