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

import fi.metropolia.practisecalorie.data.FoodViewModel;

public class History extends AppCompatActivity {


    TextView tvCalendar;

    DatePickerDialog.OnDateSetListener dateSetListener;
    String chosenDate, todayDate;
    private FoodViewModel foodViewModel;

    //For the bottom navigation View
    BottomNavigationView view;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //To get the bottomNavigation view by Id
        view = findViewById(R.id.bottomNavigation);

        //Set HISTORY selected
        view.setSelectedItemId(R.id.history);

        tvCalendar = findViewById(R.id.tvCalendar);

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
        };


        view.setOnItemSelectedListener(item -> {
            //To click on Profile, the profile activity will open
            switch (item.getItemId()) {
                case R.id.profile:
                    startActivity(new Intent(getApplicationContext(),
                            EditProfile.class));
                    overridePendingTransition(0, 0);
                    break;

                // To Click on home, it will stay in overview activity.
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(),
                            Overview.class));
                    overridePendingTransition(0, 0);
                    break;

                //To click on history it will go to history activity
                case R.id.history:
                    startActivity(new Intent(getApplicationContext(),
                         History.class));
                  overridePendingTransition(0, 0);
                    break;
            }
            return true;
        });

    }
}