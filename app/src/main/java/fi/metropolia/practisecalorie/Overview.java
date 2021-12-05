package fi.metropolia.practisecalorie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Overview extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        getSupportActionBar().hide();


        findViewById(R.id.addFoodBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent foodItemsIntent = new Intent(Overview.this, FoodItems.class);
                startActivity(foodItemsIntent);
            }
        });



        //complete day button

        findViewById(R.id.completeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent completedDayIntent = new Intent(Overview.this, CompletedDay.class);
                startActivity(completedDayIntent);
            }
        });



    }
}