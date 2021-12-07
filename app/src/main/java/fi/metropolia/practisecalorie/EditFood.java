package fi.metropolia.practisecalorie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

import fi.metropolia.practisecalorie.data.Food;

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
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Edit entry");

        updateFoodInput = findViewById(R.id.etFoodName);
        updateKcalInput = findViewById(R.id.etCaloriePer100Gram);
        updatePortionsInput = findViewById(R.id.etPortion);
        confirmChanges = findViewById(R.id.addToDay);
        updateTvNumTotalCalorie = findViewById(R.id.tvNumTotalCalorie);

//        Bundle fromEditIntent = getIntent().getExtras();
//
//
//        updateFoodInput.setText(fromEditIntent.getString("foodName"));
//        updateKcalInput.setText(fromEditIntent.getString("kcalPerPortion"));
//        updatePortionsInput.setText(fromEditIntent.getString("portion"));
//        updateTvNumTotalCalorie.setText(fromEditIntent.getString("totalKcalPerEntry"));

//
//
//        confirmChanges.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//                Intent afterEditIntent = new Intent();
//                setResult(EDIT_FOOD_REQUEST,afterEditIntent);
//
//            }
//        });
    }
}