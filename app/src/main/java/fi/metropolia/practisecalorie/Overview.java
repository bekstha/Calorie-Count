package fi.metropolia.practisecalorie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fi.metropolia.practisecalorie.data.FoodViewModel;

public class Overview extends AppCompatActivity {

    //trying to update progress bar
    TextView tvCalorieConsumedNum, tvTotalCalorieRequirement;


    String foodName;
    double kcalPerPortion, portion, kcalPerEntry;

    private FoodViewModel foodViewModel;

    private final String TAG = this.getClass().getSimpleName();

    double sumConsumedCalorie;

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//        if (result != null && result.getResultCode() == RESULT_OK) {
//            if (result.getData() != null
//                    && result.getData().getStringExtra(EditFoodItems.KEY_FOOD_NAME) != null
//                    && result.getData().getStringExtra(EditFoodItems.KEY_FOOD_KCAL) != null
//                    && result.getData().getStringExtra(EditFoodItems.KEY_PORTIONS) != null
//                    && result.getData().getStringExtra(EditFoodItems.KEY_ENTRY_KCAL) != null) {
//                foodName = result.getData().getStringExtra(EditFoodItems.KEY_FOOD_NAME);
//                kcalPerPortion = Double.parseDouble(result.getData().getStringExtra(EditFoodItems.KEY_FOOD_KCAL));
//                portion = Double.parseDouble(result.getData().getStringExtra(EditFoodItems.KEY_PORTIONS));
//                kcalPerEntry = Double.parseDouble(result.getData().getStringExtra(EditFoodItems.KEY_ENTRY_KCAL));
//
//                Toast.makeText(getApplicationContext(), "Entry saved! ", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(getApplicationContext(), " Entry cannot be saved!", Toast.LENGTH_SHORT).show();
//            }
//        }
//        sumConsumedCalorie = sumConsumedCalorie + kcalPerEntry;
        tvCalorieConsumedNum.setText(String.valueOf(sumConsumedCalorie));
    });




//    ActivityResultLauncher<Intent> startForEditResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//        if(result != null && result.getResultCode() == EDIT_FOOD_REQUEST){
//            if(result.getData() != null && result.getData().getStringExtra(EditFoodItems.KEY_FOOD_ID) != null){
//                int id = result.getData().getIntExtra(EditFoodItems.KEY_FOOD_ID, -1);
//                if (id == -1){
//                    Toast.makeText(getApplicationContext(),"Entry cannot be updated!", Toast.LENGTH_SHORT).show();
//                }
//                foodName = result.getData().getStringExtra(EditFoodItems.KEY_FOOD_NAME);
//                kcalPerPortion = Double.parseDouble(result.getData().getStringExtra(EditFoodItems.KEY_FOOD_KCAL));
//                portion = Double.parseDouble(result.getData().getStringExtra(EditFoodItems.KEY_PORTIONS));
//                kcalPerEntry = Double.parseDouble(result.getData().getStringExtra(EditFoodItems.KEY_ENTRY_KCAL));
//
//                Food food = new Food(id, LocalDate.now(),foodName, kcalPerPortion, portion,kcalPerEntry);
//                foodViewModel.update(food);
//                Toast.makeText(getApplicationContext(), "Entry was updated!", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        Log.d("Overview", "On create");

        tvTotalCalorieRequirement = findViewById(R.id.tvCalorieRequirement);
        tvCalorieConsumedNum = findViewById(R.id.tvTotalCalorieNum);
        sumConsumedCalorie = 0;

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FoodAdapter adapter = new FoodAdapter();
        recyclerView.setAdapter(adapter);

        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        foodViewModel.getAllFoods().observe(this, adapter::setFoods);

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
//        adapter.setOnItemClickListener(new FoodAdapter.onItemClickListener() {
//            @Override
//            public void onItemClick(Food food) {
//                Intent editIntent = new Intent(Overview.this, EditFoodItems.class);
//                editIntent.putExtra(EditFoodItems.KEY_FOOD_ID, food.getId());
//                editIntent.putExtra(EditFoodItems.KEY_FOOD_NAME, food.getFoodName());
//                editIntent.putExtra(EditFoodItems.KEY_FOOD_KCAL, food.getKcalPerPortion());
//                editIntent.putExtra(EditFoodItems.KEY_PORTIONS, food.getPortion());
//                editIntent.putExtra(EditFoodItems.KEY_ENTRY_KCAL, food.getTotalKcalPerEntry());
//                setResult(EDIT_FOOD_REQUEST,editIntent);
//                startForEditResult.launch(editIntent);
//            }
//        });


        //addFood
        findViewById(R.id.addFoodBtn).setOnClickListener(v -> {
            Intent addIntent = new Intent(Overview.this, EditFoodItems.class);
            startForResult.launch(addIntent);
        });

        //complete day button
        findViewById(R.id.completeBtn).setOnClickListener(v -> {
            Intent completedDayIntent = new Intent(Overview.this, CompletedDay.class);
            startActivity(completedDayIntent);
        });


    }
}