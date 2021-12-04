package fi.metropolia.practisecalorie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.LinkedList;
import java.util.List;

public class Overview extends AppCompatActivity {

    String[] data = {"Chicken" , "Rice", "Milk"};
    int counter = 0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        getSupportActionBar().hide();

        List <String> foodItems = new LinkedList<>();
        foodItems.add("Bread");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FoodAdapter foodAdapter = new FoodAdapter(foodItems);
        recyclerView.setAdapter(foodAdapter);

        findViewById(R.id.addFoodBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodItems.add(data[counter%3]);
                counter++;
                foodAdapter.notifyItemInserted(foodItems.size()-1);
            }
        });


        //complete day button

        findViewById(R.id.completeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Overview.this, CompletedDay.class);
                startActivity(intent);
            }
        });



    }
}