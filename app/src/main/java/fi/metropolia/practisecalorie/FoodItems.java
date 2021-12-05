package fi.metropolia.practisecalorie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;

import fi.metropolia.practisecalorie.data.Food;
import fi.metropolia.practisecalorie.data.FoodDB;

public class FoodItems extends AppCompatActivity {

    EditText foodInput, kcalInput, portionsInput;
    Button addToDayBtn;
    TextView tvNumTotalCalorie;
    double intKcalInput, intPortions, intTotalCalorieForEntry;
    String foodName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_items);
        getSupportActionBar().hide();

        foodInput = findViewById(R.id.etFoodName);
        kcalInput = findViewById(R.id.etCaloriePer100Gram);
        portionsInput = findViewById(R.id.etPortion);
        addToDayBtn = findViewById(R.id.addToDay);
        tvNumTotalCalorie = findViewById(R.id.tvNumTotalCalorie);


        addToDayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (foodInput.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter the food name", Toast.LENGTH_SHORT).show();
                }else{
                    foodName = foodInput.getText().toString().trim();
                }

                if(kcalInput.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter the number of calories per 100gm", Toast.LENGTH_SHORT).show();
                }else if (Double.parseDouble(kcalInput.getText().toString()) <= 0) {
                    Toast.makeText(getApplicationContext(), "Kcal cannot be less or equal to o!", Toast.LENGTH_SHORT).show();
                }else{
                    intKcalInput = Double.parseDouble(kcalInput.getText().toString());
                }

                if(portionsInput.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter the portion",Toast.LENGTH_SHORT).show();
                }else if (Double.parseDouble(portionsInput.getText().toString()) <= 0) {
                    Toast.makeText(getApplicationContext(), "portions cannot be less or equal to 0!", Toast.LENGTH_SHORT).show();
                }else{
                    intPortions = Double.parseDouble(portionsInput.getText().toString());
                }

                if(intKcalInput > 0 && intPortions > 0){
                    intTotalCalorieForEntry = intKcalInput * intPortions;
                    tvNumTotalCalorie.setText("" + intTotalCalorieForEntry);
                }else{
                    tvNumTotalCalorie.setText(""+ 0);
                }



                if(intTotalCalorieForEntry <= 0){
                    Toast.makeText(getApplicationContext(),"Not added",Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(),"Added successfully!",Toast.LENGTH_SHORT).show();
                }

                FoodDB foodDB = Room.databaseBuilder(getApplicationContext(), FoodDB.class, "Food.db").allowMainThreadQueries().build();
                Food test = new Food(0, LocalDate.now(), foodName , intKcalInput, intPortions, intTotalCalorieForEntry);
                long newid = foodDB.foodDAO().create(test);

                Log.d("database", "new data created with"+ newid);



            }
        });


    }
}