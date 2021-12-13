package fi.metropolia.practisecalorie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class History extends AppCompatActivity {



    //For the bottom navigation View
    BottomNavigationView view;

    TextView tvCalendar;

    DatePickerDialog.OnDateSetListener dateSetListener;
    String chosenDate, todayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);



        //To get the bottomNavigation view by Id
        view = findViewById(R.id.bottomNavigation);



        //Set HISTORY selected

        view.setSelectedItemId(R.id.history);






        tvCalendar = findViewById(R.id.tvCalendar);

        Calendar calendar = Calendar.getInstance();

        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);






        view.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {



                //To click on Profile, the profile activity will open

                switch (item.getItemId()) {
                    case R.id.profile:
                        Toast.makeText(getApplicationContext(), "My Profile", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),
                                Profile.class));
                        overridePendingTransition(0, 0);

                        break;



                    // To Click on home, it will stay in overview activity.

                    case R.id.home:
                        Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(),
                                Overview.class));
                        overridePendingTransition(0, 0);
                        break;


                    //To click on history it will go to history activity

                    case R.id.history:
                        Toast.makeText(getApplicationContext(), "History", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(),
                             History.class));
                      overridePendingTransition(0, 0);

                        break;


                }

                return true;
            }
        });

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
            tvCalendar.setText(chosenDate);
        };
    }
}