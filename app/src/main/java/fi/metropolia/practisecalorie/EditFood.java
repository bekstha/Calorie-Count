package fi.metropolia.practisecalorie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Objects;

import fi.metropolia.practisecalorie.data.Food;
import fi.metropolia.practisecalorie.user.LoggedUser;
import fi.metropolia.practisecalorie.user.UserDatabase;

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

        confirmChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (updateFoodInput.getText().toString().trim().isEmpty() || updateKcalInput.getText().toString().trim().isEmpty() || updatePortionsInput.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "All fields required!", Toast.LENGTH_SHORT).show();
                    return;
                } else if ((Double.parseDouble(updateKcalInput.getText().toString()) <= 0) || Double.parseDouble(updatePortionsInput.getText().toString()) <= 0) {
                    Toast.makeText(getApplicationContext(), "Values cannot be less or equal to 0!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    udFoodName = updateFoodInput.getText().toString().trim();
                    udIntKcalInput = Double.parseDouble(updateKcalInput.getText().toString());
                    udIntPortions = Double.parseDouble(updatePortionsInput.getText().toString());
                }
                udIntTotalCalorieForEntry = udIntKcalInput * udIntPortions;
                updateTvNumTotalCalorie.setText("" + udIntTotalCalorieForEntry);

                LoggedUser loggedUser =LoggedUser.getInstance();
                Food food = new Food(LocalDate.now(),udFoodName,udIntKcalInput, udIntPortions, udIntTotalCalorieForEntry, LoggedUser.getUserID());

                Toast.makeText(getApplicationContext(), "" + food , Toast.LENGTH_SHORT).show();
                UserDatabase foodDb = UserDatabase.getUserDatabase(getApplicationContext());
                foodDb.foodDAO().update(food);
            }
        });
    }
}