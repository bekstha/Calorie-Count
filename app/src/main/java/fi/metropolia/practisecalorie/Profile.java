package fi.metropolia.practisecalorie;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class Profile extends AppCompatActivity {

    RelativeLayout maleLayout, femaleLayout;
    SeekBar seekBarHeight;
    int intCurrentHeight;
    TextView currentHeight, tvDOB, tvWeight;
    ImageView weightIncrement, weightDecrement;
    int age;
    String birthDate, todayDate;

    String typeOfUser = "0";
    int intWeight = 60;

    DatePickerDialog.OnDateSetListener dateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Objects.requireNonNull(getSupportActionBar()).hide();

        maleLayout = findViewById(R.id.male);
        femaleLayout = findViewById(R.id.female);
        seekBarHeight = findViewById(R.id.seekBarHeight);
        currentHeight = findViewById(R.id.currentHeight);
        tvDOB = findViewById(R.id.tvDOB);
        weightIncrement = findViewById(R.id.weightIncrement);
        weightDecrement = findViewById(R.id.weightDecrement);
        tvWeight = findViewById(R.id.tvWeight);

        //setting on-click Listener for the male and female layout
        maleLayout.setOnClickListener(v -> {
            maleLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.focus));
            femaleLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.no_focus));
            typeOfUser = "Male";
        });

        femaleLayout.setOnClickListener(v -> {
            femaleLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.focus));
            maleLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.no_focus));
            typeOfUser = "Female";
        });


        //seekBar listener for the height
        seekBarHeight.setMax(300);
        seekBarHeight.setProgress(170);
        intCurrentHeight = 170;
        seekBarHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                intCurrentHeight = progress;
                currentHeight.setText(String.valueOf(intCurrentHeight));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // On-click listener for both image buttons that increases and decreases the weight
        weightIncrement.setOnClickListener(v -> {
            intWeight = intWeight+1;
            tvWeight.setText(String.valueOf(intWeight));
        });

        weightDecrement.setOnClickListener(v -> {
            intWeight = intWeight-1;
            tvWeight.setText(String.valueOf(intWeight));
        });



        // Calling Calender class from java util and setting on click listener
        Calendar calendar = Calendar.getInstance();

        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        todayDate = simpleDateFormat.format(Calendar.getInstance().getTime());

        tvDOB.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),dateSetListener,year,month,day);
            datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
            datePickerDialog.show();
        });

        dateSetListener = (view, year1, month1, dayOfMonth) -> {
            // month starts from 0 in java so need to add 1 to it
            month1 = month1 +1;
            birthDate = dayOfMonth + "/" + month1 + "/" + year1;
            tvDOB.setText(birthDate);
        };

        // Calculate Button Listener
        Button calculateBtn = findViewById(R.id.calculateBtn);
        calculateBtn.setOnClickListener(v -> {

            // checking if date of birth is selected and then calculating user's age
            if(birthDate == null){
                Toast.makeText(getApplicationContext(), "Enter your birth date", Toast.LENGTH_SHORT).show();
            }else{
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date1 = simpleDateFormat1.parse(birthDate);
                    Date date2 = simpleDateFormat.parse(todayDate);

                    assert date1 != null;
                    long startDate = date1.getTime();
                    assert date2 != null;
                    long endDate = date2.getTime();

                    Period period = new Period(startDate,endDate, PeriodType.yearMonthDay());
                    age = period.getYears();


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            // if-statements to check that all the parameters are entered by the user and only then
            // go on to the next activity
            if(typeOfUser.equals("0")){

                Toast.makeText(getApplicationContext(),"Select a gender",Toast.LENGTH_SHORT).show();

            }else if(intCurrentHeight == 0){

                Toast.makeText(getApplicationContext(),"Select a height",Toast.LENGTH_SHORT).show();

            }else if( intWeight <= 0){

                Toast.makeText(getApplicationContext(),"Enter a valid weight",Toast.LENGTH_SHORT).show();

            }else{
                Intent intent = new Intent(Profile.this, CalorieRequirement.class);
                intent.putExtra("userGender",typeOfUser);
                intent.putExtra("height", intCurrentHeight);
                intent.putExtra("weight", intWeight);
                intent.putExtra("age", age);
                startActivity(intent);
            }

        });
     }

}