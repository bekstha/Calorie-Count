package fi.metropolia.practisecalorie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class EditFood extends AppCompatActivity {

    EditText etUpdateFoodName,etUpdateCaloriePer100Gram,etPortion;
    TextView tvNumTotalCalorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);

        Intent intent = getIntent();

        etUpdateFoodName.setText(intent.getStringExtra(Overview.KEY_EDIT_FOOD_NAME));
        etUpdateCaloriePer100Gram.setText(String.valueOf(intent.getStringExtra(Overview.KEY_EDIT_FOOD_KCAL)));
        etPortion.setText(intent.getStringExtra(Overview.KEY_EDIT_PORTIONS));
        tvNumTotalCalorie.setText(intent.getStringExtra(Overview.KEY_EDIT_ENTRY_KCAL));




    }
}