package fi.metropolia.practisecalorie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.UUID;

import fi.metropolia.practisecalorie.data.Food;
import fi.metropolia.practisecalorie.data.FoodViewModel;

public class Overview extends AppCompatActivity {

    public static final String EXTRA_ID = "Food Id";
    public static final String KEY_EDIT_FOOD_NAME = "Food Name";
    public static final String KEY_EDIT_FOOD_KCAL= "Kcal per 100 gram";
    public static final String KEY_EDIT_PORTIONS = "portions";
    public static final String KEY_EDIT_ENTRY_KCAL = "total calories for entry";

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

    ActivityResultLauncher<Intent> startForEditResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                foodViewModel.delete(adapter.getFoodAt(viewHolder.getAbsoluteAdapterPosition()));
                Toast.makeText(Overview.this, "Entry deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        //editFood
        adapter.setOnItemClickListener(new FoodAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Food food) {
                Intent editIntent = new Intent(Overview.this, EditFood.class);
                editIntent.putExtra(EXTRA_ID, food.getId());
                editIntent.putExtra(KEY_EDIT_FOOD_NAME, food.getFoodName());
                editIntent.putExtra(KEY_EDIT_FOOD_KCAL, food.getKcalPerPortion());
                editIntent.putExtra(KEY_EDIT_PORTIONS, food.getPortion());
                editIntent.putExtra(KEY_EDIT_ENTRY_KCAL, food.getTotalKcalPerEntry());
                startForEditResult.launch(editIntent);
            }
        });




        //addFood
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