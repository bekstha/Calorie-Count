package fi.metropolia.practisecalorie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditFood extends AppCompatActivity {

    EditText etUpdateFoodName,etUpdateCaloriePer100Gram,etPortion;
    TextView tvNumTotalCalorie;

    public static final String EDITED_FOOD = "Food Name";
    public static final String EDITED_FOOD_KCAL= "Kcal per 100 gram";
    public static final String EDITED_PORTIONS = "portions";
    public static final String EDITED_ENTRY_KCAL = "total calories for entry";

    EditText foodInput, kcalInput, portionsInput;
    Button addToDayBtn;
    double intKcalInput, intPortions, intTotalCalorieForEntry;
    String foodName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);

        foodInput = findViewById(R.id.etFoodName);
        kcalInput = findViewById(R.id.etCaloriePer100Gram);
        portionsInput = findViewById(R.id.etPortion);
        addToDayBtn = findViewById(R.id.addToDay);
        tvNumTotalCalorie = findViewById(R.id.tvNumTotalCalorie);

        Intent intent = getIntent();
        etUpdateFoodName.setText(intent.getStringExtra(Overview.KEY_EDIT_FOOD_NAME));
        etUpdateCaloriePer100Gram.setText(String.valueOf(intent.getStringExtra(Overview.KEY_EDIT_FOOD_KCAL)));
        etPortion.setText(intent.getStringExtra(Overview.KEY_EDIT_PORTIONS));
        tvNumTotalCalorie.setText(intent.getStringExtra(Overview.KEY_EDIT_ENTRY_KCAL));

        findViewById(R.id.confirmChanges).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(EDITED_FOOD, foodName);
                intent.putExtra(EDITED_FOOD_KCAL, intKcalInput);
                intent.putExtra(EDITED_FOOD_KCAL, intPortions);
                intent.putExtra(EDITED_FOOD_KCAL, intTotalCalorieForEntry);
                setResult(RESULT_OK, intent);
                finish();
            }
        });



    }
}