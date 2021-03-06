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
 * Activity where the user can edit their food entry
 */
public class EditFood extends AppCompatActivity {

    EditText updateFoodInput, updateKcalInput, updatePortionsInput;
    Button confirmChanges;
    TextView updateTvNumTotalCalorie;
    double udIntKcalInput, udIntPortions, udIntTotalCalorieForEntry;
    String udFoodName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);
        Objects.requireNonNull(getSupportActionBar()).hide();

        updateFoodInput = findViewById(R.id.etUpdateFoodName);
        updateKcalInput = findViewById(R.id.etUpdateCaloriePer100Gram);
        updatePortionsInput = findViewById(R.id.etUpdatePortion);
        confirmChanges = findViewById(R.id.confirmChanges);
        updateTvNumTotalCalorie = findViewById(R.id.tvNumTotalCalorie);

        Bundle fromEditIntent = getIntent().getExtras();
        updateFoodInput.setText(fromEditIntent.getString("foodName"));
        updateKcalInput.setText(fromEditIntent.getString("kcalPerPortion"));
        updatePortionsInput.setText(fromEditIntent.getString("portion"));
        updateTvNumTotalCalorie.setText(fromEditIntent.getString("totalKcalPerEntry"));

        //click listener
        confirmChanges.setOnClickListener(v -> {
            //checking if all the fields are filled by the user
            if (updateFoodInput.getText().toString().trim().isEmpty() ||
                    updateKcalInput.getText().toString().trim().isEmpty() ||
                    updatePortionsInput.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.fields_required), Toast.LENGTH_SHORT).show();
                return;
                //checking if user inputs 0 or less
            } else if ((Double.parseDouble(updateKcalInput.getText().toString()) <= 0) ||
                    Double.parseDouble(updatePortionsInput.getText().toString()) <= 0) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.values_less_than_zero), Toast.LENGTH_SHORT).show();
                return;
            } else {
                udFoodName = updateFoodInput.getText().toString().trim();
                udIntKcalInput = Double.parseDouble(updateKcalInput.getText().toString());
                udIntPortions = Double.parseDouble(updatePortionsInput.getText().toString());
            }
            //updating the total calorie for the entry
            udIntTotalCalorieForEntry = udIntKcalInput * udIntPortions;
            updateTvNumTotalCalorie.setText(String.valueOf(udIntTotalCalorieForEntry));

            Food food = new Food(LocalDate.now(), udFoodName, udIntKcalInput, udIntPortions, udIntTotalCalorieForEntry, LoggedUser.getUserID());
            food.setId(fromEditIntent.getLong("FoodId"));

            //updating the food entry
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.entry_successfully_updated), Toast.LENGTH_SHORT).show();
            UserDatabase foodDb = UserDatabase.getUserDatabase(getApplicationContext());
            foodDb.foodDAO().update(food);

            Intent intent = new Intent(EditFood.this, Overview.class);
            startActivity(intent);


        });
    }
}