package fi.metropolia.practisecalorie;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.time.LocalDate;

import fi.metropolia.practisecalorie.user.LoggedUser;
import fi.metropolia.practisecalorie.user.UserDatabase;
import fi.metropolia.practisecalorie.data.FoodDAO;
import fi.metropolia.practisecalorie.data.FoodViewModel;

public class Overview extends AppCompatActivity {
    public static final String KEY_FOOD_ID = "Food ID";
    public static final String KEY_FOOD_NAME = "Food Name";
    public static final String KEY_FOOD_KCAL = "Kcal per 100 gram";
    public static final String KEY_PORTIONS = "portions";
    public static final String KEY_ENTRY_KCAL = "total calories for entry";

    //trying to update progress bar
    TextView tvCalorieConsumedNum, tvTotalCalorieRequirement;

    private FoodViewModel foodViewModel;
    FoodAdapter adapter;

    private final String TAG = this.getClass().getSimpleName();

    double sumConsumedCalorie;

    double calorieRequirement;

    CircularProgressIndicator circularProgressIndicator;

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        foodViewModel.getAllFoods().observe(this, adapter::setFoods);
        UserDatabase foodDB = UserDatabase.getUserDatabase(getApplicationContext());
        final FoodDAO foodDAO = foodDB.foodDAO();
        sumConsumedCalorie = foodDAO.getTotal(LocalDate.now(), LoggedUser.getUserID());
        tvCalorieConsumedNum.setText(String.valueOf(sumConsumedCalorie));
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        getSupportActionBar().setTitle("Home");


        Log.d("Overview", "On create");

        tvTotalCalorieRequirement = findViewById(R.id.tvTotalCalorieRequirement);
        tvCalorieConsumedNum = findViewById(R.id.tvConsumedCalorieNum);

        UserDatabase foodDB = UserDatabase.getUserDatabase(getApplicationContext());
        final FoodDAO foodDAO = foodDB.foodDAO();
        sumConsumedCalorie = foodDAO.getTotal(LocalDate.now(), LoggedUser.getUserID());
        tvCalorieConsumedNum.setText(String.valueOf(sumConsumedCalorie));

        UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
        LoggedUser loggedUser = LoggedUser.getInstance();
        calorieRequirement = userDatabase.userDao().searchCalorieRequirement(LoggedUser.getUserID());
        Toast.makeText(getApplicationContext(),"calorieRequirement : " + calorieRequirement, Toast.LENGTH_SHORT).show();
        tvTotalCalorieRequirement.setText(String.valueOf("of "+calorieRequirement + " Kcal"));



        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FoodAdapter();
        recyclerView.setAdapter(adapter);


        // have to make change here so that only the logged in user can view their food
        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        foodViewModel.getAllFoods().observe(this, adapter::setFoods);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int viewPosition = viewHolder.getAbsoluteAdapterPosition();

                switch(direction){
                    case ItemTouchHelper.LEFT:
                        foodViewModel.delete(adapter.getFoodAt(viewHolder.getAbsoluteAdapterPosition()));
                        Toast.makeText(Overview.this, "Entry deleted", Toast.LENGTH_SHORT).show();
                        UserDatabase foodDB = UserDatabase.getUserDatabase(getApplicationContext());
                        final FoodDAO foodDAO = foodDB.foodDAO();
                        sumConsumedCalorie = foodDAO.getTotal(LocalDate.now(), LoggedUser.getUserID());
                        tvCalorieConsumedNum.setText(String.valueOf(sumConsumedCalorie));

                }
            }

        }).attachToRecyclerView(recyclerView);

        //addFood
        findViewById(R.id.addFoodBtn).setOnClickListener(v -> {
            Intent addIntent = new Intent(Overview.this, AddFoodItems.class);
            startForResult.launch(addIntent);
        });

        //complete day button
        findViewById(R.id.completeBtn).setOnClickListener(v -> {
            Intent completedDayIntent = new Intent(Overview.this, EditProfile.class);
            startActivity(completedDayIntent);
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(Overview.this);

                builder.setTitle("logging out")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent logoutIntent = new Intent(Overview.this, MainActivity.class);
                                startActivity(logoutIntent);
                            }
                        });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}