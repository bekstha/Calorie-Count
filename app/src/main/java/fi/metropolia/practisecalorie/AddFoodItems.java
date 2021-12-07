package fi.metropolia.practisecalorie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.time.LocalDate;

import fi.metropolia.practisecalorie.data.Food;
import fi.metropolia.practisecalorie.data.FoodDB;

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

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("New entry");

        foodInput = findViewById(R.id.etFoodName);
        kcalInput = findViewById(R.id.etCaloriePer100Gram);
        portionsInput = findViewById(R.id.etPortion);
        addToDayBtn = findViewById(R.id.addToDay);
        tvNumTotalCalorie = findViewById(R.id.tvNumTotalCalorie);

//        Intent fromEditIntent = getIntent();
//        foodInput.setText(fromEditIntent.getStringExtra(Overview.KEY_FOOD_NAME));
//        kcalInput.setText(fromEditIntent.getStringExtra(Overview.KEY_FOOD_KCAL));
//        portionsInput.setText(fromEditIntent.getStringExtra(Overview.KEY_PORTIONS));
//        tvNumTotalCalorie.setText(fromEditIntent.getStringExtra(Overview.KEY_ENTRY_KCAL));


        addToDayBtn.setOnClickListener(v -> {
            saveFood();
        });

    }

    private void saveFood() {
        if (foodInput.getText().toString().isEmpty() || kcalInput.getText().toString().isEmpty() || portionsInput.getText().toString().isEmpty()) {
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

        FoodDB foodDB = FoodDB.get(this);

        Food test = new Food(LocalDate.now(), foodName, intKcalInput, intPortions, intTotalCalorieForEntry);
        Long newId = foodDB.foodDAO().create(test);
        Log.d(TAG, "Added " + test + " database");

        Intent intent = new Intent();
//            intent.putExtra(KEY_FOOD_NAME, foodName);
//            intent.putExtra(KEY_FOOD_KCAL, String.valueOf(intKcalInput));
//            intent.putExtra(KEY_PORTIONS, String.valueOf(intPortions));
//            intent.putExtra(KEY_ENTRY_KCAL, String.valueOf(intTotalCalorieForEntry));
        setResult(RESULT_OK, intent);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_food_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_food:
                saveFood();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}