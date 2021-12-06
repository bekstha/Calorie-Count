package fi.metropolia.practisecalorie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.UUID;

import fi.metropolia.practisecalorie.data.Food;
import fi.metropolia.practisecalorie.data.FoodViewModel;

public class Overview extends AppCompatActivity {

    private FoodViewModel foodViewModel;

    private final String TAG = this.getClass().getSimpleName();

    private RecyclerView recyclerView;

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result != null && result.getResultCode() == RESULT_OK){
            if(result.getData() != null && result.getData().getStringExtra(FoodItems.KEY_FOOD_NAME) != null){
                Toast.makeText(getApplicationContext(),"DONE!", Toast.LENGTH_SHORT).show();
            }
        }

    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FoodAdapter adapter = new FoodAdapter();
        recyclerView.setAdapter(adapter);

        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        foodViewModel.getAllFoods().observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                adapter.setFoods(foods);
            }
        });

        findViewById(R.id.addFoodBtn).setOnClickListener(v -> {
            Intent intent = new Intent(Overview.this, FoodItems.class);
            startForResult.launch(intent);
        });


        //complete day button
        findViewById(R.id.completeBtn).setOnClickListener(v -> {
            Intent completedDayIntent = new Intent(Overview.this, CompletedDay.class);
            startActivity(completedDayIntent);
        });




    }
}