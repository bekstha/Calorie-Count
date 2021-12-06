package fi.metropolia.practisecalorie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FoodItems extends AppCompatActivity {

    EditText foodInput, kcalInput, portionsInput;
    Button addToDayBtn;
    TextView tvNumTotalCalorie;
    double intKcalInput, intPortions, intTotalCalorieForEntry;
    String foodName;

    public static final String KEY_FOOD_NAME = "Food Name";
    public static final String KEY_FOOD_KCAL= "Kcal per 100 gram";
    public static final String KEY_PORTIONS = "portions";
    public static final String KEY_ENTRY_KCAL = "total calories for entry";


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


        addToDayBtn.setOnClickListener(v -> {

            if (foodInput.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Enter the food name", Toast.LENGTH_SHORT).show();
                return;
            }else{
                foodName = foodInput.getText().toString().trim();
            }

            if(kcalInput.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Enter the number of calories per 100gm", Toast.LENGTH_SHORT).show();
                return;
            }else if (Double.parseDouble(kcalInput.getText().toString()) <= 0) {
                Toast.makeText(getApplicationContext(), "Kcal cannot be less or equal to o!", Toast.LENGTH_SHORT).show();
                return;
            }else{
                intKcalInput = Double.parseDouble(kcalInput.getText().toString());
            }

            if(portionsInput.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Enter the portion",Toast.LENGTH_SHORT).show();
                return;
            }else if (Double.parseDouble(portionsInput.getText().toString()) <= 0) {
                Toast.makeText(getApplicationContext(), "portions cannot be less or equal to 0!", Toast.LENGTH_SHORT).show();
                return;
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
                Intent intent = new Intent();
                intent.putExtra(KEY_FOOD_NAME, foodName);
                intent.putExtra(KEY_FOOD_KCAL, intKcalInput);
                intent.putExtra(KEY_PORTIONS, intPortions);
                intent.putExtra(KEY_ENTRY_KCAL, intTotalCalorieForEntry);
                setResult(RESULT_OK, intent);
                finish();
            }

        });


    }
}