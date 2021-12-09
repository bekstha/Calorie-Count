package fi.metropolia.practisecalorie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;

import fi.metropolia.practisecalorie.user.LoggedUser;
import fi.metropolia.practisecalorie.user.UserDatabase;
import fi.metropolia.practisecalorie.data.Food;

public class AddFoodItems extends AppCompatActivity {

    EditText foodInput, kcalInput, portionsInput;
    Button addToDayBtn;
    TextView tvNumTotalCalorie;
    double intKcalInput, intPortions, intTotalCalorieForEntry;
    String foodName;

    public static final String TAG = "ROOM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_items);

        foodInput = findViewById(R.id.etFoodName);
        kcalInput = findViewById(R.id.etCaloriePer100Gram);
        portionsInput = findViewById(R.id.etPortion);
        addToDayBtn = findViewById(R.id.addToDay);
        tvNumTotalCalorie = findViewById(R.id.tvNumTotalCalorie);


        addToDayBtn.setOnClickListener(v -> {
            if (foodInput.getText().toString().trim().isEmpty() || kcalInput.getText().toString().trim().isEmpty() || portionsInput.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "All fields required!", Toast.LENGTH_SHORT).show();
                return;
            } else if ((Double.parseDouble(kcalInput.getText().toString()) <= 0) || Double.parseDouble(portionsInput.getText().toString()) <= 0) {
                Toast.makeText(getApplicationContext(), "Values cannot be less or equal to 0!", Toast.LENGTH_SHORT).show();
                return;
            } else {
                foodName = foodInput.getText().toString().trim();
                intKcalInput = Double.parseDouble(kcalInput.getText().toString());
                intPortions = Double.parseDouble(portionsInput.getText().toString());
            }

            intTotalCalorieForEntry = intKcalInput * intPortions;
            tvNumTotalCalorie.setText("" + intTotalCalorieForEntry);

            UserDatabase foodDB = UserDatabase.getUserDatabase(this);

            Food test = new Food(LocalDate.now(), foodName, intKcalInput, intPortions, intTotalCalorieForEntry, LoggedUser.getUserID());
            Long newId = foodDB.foodDAO().create(test);

            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        });
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.add_food_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.save_food:
//                saveFood();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//
//        }
//    }
}