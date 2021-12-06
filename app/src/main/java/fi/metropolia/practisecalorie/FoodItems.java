package fi.metropolia.practisecalorie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.time.LocalDate;

import fi.metropolia.practisecalorie.data.Food;
import fi.metropolia.practisecalorie.data.FoodDB;

public class FoodItems extends AppCompatActivity {

    EditText foodInput, kcalInput, portionsInput;
    Button addToDayBtn;
    TextView tvNumTotalCalorie;
    double intKcalInput, intPortions, intTotalCalorieForEntry;
    String foodName;

    public static final String TAG = "ROOM";
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

            if (foodInput.getText().toString().isEmpty() || kcalInput.getText().toString().isEmpty() || portionsInput.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "All fields required!", Toast.LENGTH_SHORT).show();
                return;
            }else if ((Double.parseDouble(kcalInput.getText().toString()) <= 0) || Double.parseDouble(portionsInput.getText().toString()) <= 0){
                Toast.makeText(getApplicationContext(), "Values cannot be less or equal to 0!", Toast.LENGTH_SHORT).show();
                return;
            }else{
                foodName = foodInput.getText().toString().trim();
                intKcalInput = Double.parseDouble(kcalInput.getText().toString());
                intPortions = Double.parseDouble(portionsInput.getText().toString());
            }

            intTotalCalorieForEntry = intKcalInput * intPortions;
            tvNumTotalCalorie.setText("" + intTotalCalorieForEntry);

            Toast.makeText(getApplicationContext(),"Added successfully!",Toast.LENGTH_SHORT).show();

            FoodDB foodDB = FoodDB.get(this);

            Food test = new Food(0,LocalDate.now(), foodName, intKcalInput, intPortions, intTotalCalorieForEntry);
            Long newId = foodDB.foodDAO().create(test);
            Log.d(TAG,"new food added to database with new id " + newId);

            Intent intent = new Intent();
            intent.putExtra(KEY_FOOD_NAME, foodName);
            intent.putExtra(KEY_FOOD_KCAL, intKcalInput);
            intent.putExtra(KEY_PORTIONS, intPortions);
            intent.putExtra(KEY_ENTRY_KCAL, intTotalCalorieForEntry);
            setResult(RESULT_OK, intent);
            finish();

        });

    }

}