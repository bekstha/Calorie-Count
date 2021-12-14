package fi.metropolia.practisecalorie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.Objects;

import fi.metropolia.practisecalorie.data.Food;
import fi.metropolia.practisecalorie.user.LoggedUser;
import fi.metropolia.practisecalorie.user.UserDatabase;

/**
 * The activity where the user can add the food to the database
 */
public class AddFoodItems extends AppCompatActivity {

    EditText foodInput, kcalInput, portionsInput;
    Button addToDayBtn;
    TextView tvNumTotalCalorie;
    double intKcalInput, intPortions, intTotalCalorieForEntry;
    String foodName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_items);
        Objects.requireNonNull(getSupportActionBar()).hide();

        foodInput = findViewById(R.id.etFoodName);
        kcalInput = findViewById(R.id.etCaloriePer100Gram);
        portionsInput = findViewById(R.id.etPortion);
        addToDayBtn = findViewById(R.id.addToDay);
        tvNumTotalCalorie = findViewById(R.id.tvNumTotalCalorie);

        //click listener to add the food to the database
        addToDayBtn.setOnClickListener(v -> {
            //checking if all the fields are input by the user
            if (foodInput.getText().toString().trim().isEmpty() ||
                    kcalInput.getText().toString().trim().isEmpty() ||
                    portionsInput.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.fields_required), Toast.LENGTH_SHORT).show();
                return;
            } else if ((Double.parseDouble(kcalInput.getText().toString()) <= 0) ||
                    Double.parseDouble(portionsInput.getText().toString()) <= 0) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.values_less_than_zero), Toast.LENGTH_SHORT).show();
                return;
            } else {
                foodName = foodInput.getText().toString().trim();
                intKcalInput = Double.parseDouble(kcalInput.getText().toString());
                intPortions = Double.parseDouble(portionsInput.getText().toString());
            }
            //calculates the total calories for that particular entry
            intTotalCalorieForEntry = intKcalInput * intPortions;
            tvNumTotalCalorie.setText(String.valueOf(intTotalCalorieForEntry));

            //inserting the created food in the database
            UserDatabase foodDB = UserDatabase.getUserDatabase(this);
            Food food = new Food(LocalDate.now(), foodName, intKcalInput, intPortions, intTotalCalorieForEntry, LoggedUser.getUserID());
            foodDB.foodDAO().create(food);

            //going back to the overview page after a successful entry
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        });
    }

}