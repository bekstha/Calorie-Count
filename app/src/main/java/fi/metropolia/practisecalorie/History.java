package fi.metropolia.practisecalorie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import fi.metropolia.practisecalorie.data.FoodDAO;
import fi.metropolia.practisecalorie.data.FoodViewModel;
import fi.metropolia.practisecalorie.user.LoggedUser;
import fi.metropolia.practisecalorie.user.UserDatabase;

public class History extends AppCompatActivity {

    TextView tvCalendar;

    DatePickerDialog.OnDateSetListener dateSetListener;
    String chosenDate, todayDate;
    private FoodViewModel foodViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


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
            tvCalendar.setText(chosenDate);

            Toast.makeText(getApplicationContext(),""+ year1 +"/" + month1 + "/" + dayOfMonth , Toast.LENGTH_SHORT).show();


            FoodAdapter adapter =new FoodAdapter();
            historyRecyclerView.setAdapter(adapter);

            foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
            foodViewModel.getAllFoods().observe(this, adapter::setFoods);



        };



    }
}