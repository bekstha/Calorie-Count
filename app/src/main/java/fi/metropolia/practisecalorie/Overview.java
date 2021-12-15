package fi.metropolia.practisecalorie;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.time.LocalDate;
import java.util.Objects;

import fi.metropolia.practisecalorie.data.FoodDAO;
import fi.metropolia.practisecalorie.data.FoodViewModel;
import fi.metropolia.practisecalorie.user.LoggedUser;
import fi.metropolia.practisecalorie.user.UserDatabase;

/**
 * In this activity, the user can see the food entry of their day.
 */
public class Overview extends AppCompatActivity {

    TextView tvCalorieConsumedNum, tvTotalCalorieRequirement;
    double sumConsumedCalorie, calorieRequirement;
    private FoodViewModel foodViewModel;
    FoodAdapter adapter;
    CircularProgressIndicator circularProgressIndicator;
    BottomNavigationView bottomNavigationOverview;

    //After user successfully adds the food then user is directed back to overview activity and
    //the recycler view, corresponding text views and progress bar are updated
    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> updateUI());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.home));

        tvTotalCalorieRequirement = findViewById(R.id.tvTotalCalorieRequirement);
        tvCalorieConsumedNum = findViewById(R.id.tvConsumedCalorieNum);
        circularProgressIndicator = findViewById(R.id.progressCircular);

        bottomNavigationOverview = findViewById(R.id.bottomNavigationOverview);
        bottomNavigationOverview.setSelectedItemId(R.id.home);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FoodAdapter();
        recyclerView.setAdapter(adapter);
        //updating the UI when user starts the application
        updateUI();


        //item touch helper that allows user to swipe on each item so that user can delete their
        //entry and updates database as well as the view and corresponding text views
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int viewPosition = viewHolder.getAbsoluteAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    foodViewModel.delete(adapter.getFoodAt(viewPosition));
                    Toast.makeText(Overview.this, "Entry deleted", Toast.LENGTH_SHORT).show();
                    // updating the UI when user deletes a food entry
                    updateUI();
                }
            }

        }).attachToRecyclerView(recyclerView);

        //setting on click listener to take user to another activity where the user can add the food
        findViewById(R.id.addFoodBtn).setOnClickListener(v -> {
            Intent addIntent = new Intent(Overview.this, AddFoodItems.class);
            startForResult.launch(addIntent);
        });

        //setting on click listener on complete button so that the user can complete their day
        findViewById(R.id.completeBtn).setOnClickListener(v -> {
            Intent completedDayIntent = new Intent(Overview.this, CompletedDay.class);
            startActivity(completedDayIntent);
        });


        bottomNavigationOverview.setOnItemSelectedListener(item -> {
            //To click on Profile, the profile activity will open
            if (item.getItemId() == R.id.profile) {

                startActivity(new Intent(getApplicationContext(), EditProfile.class));
                overridePendingTransition(0, 0);

                // To Click on home, it will stay in overview activity.
            } else if (item.getItemId() == R.id.home) {
                startActivity(new Intent(getApplicationContext(), Overview.class));
                overridePendingTransition(0, 0);

                //To click on history it will go to history activity
            } else {
                startActivity(new Intent(getApplicationContext(), History.class));
                overridePendingTransition(0, 0);
            }
            return true;
        });


    }

    // creating a menu from which the user can logout from the application
    /**
     * Inflating the top bar with a menu
     * @param menu that allows user to see option to logout
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflating menu with the logout item that was created in the drawables
        //https://youtu.be/oh4YOj9VkVE
        inflater.inflate(R.menu.top_logout_menu, menu);
        return true;
    }

    /**
     * Menu item which shows logout option to the user, on which user can click to logout from the
     * application
     * @param item logout item
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            //when logout is clicked a dialog box appears to ask confirmation from the user
            //https://www.youtube.com/watch?v=MXDlY0n6mkc&t=254s
            AlertDialog.Builder builder = new AlertDialog.Builder(Overview.this);
            builder.setTitle(getResources().getString(R.string.log_out))
                    .setCancelable(false)
                    .setMessage(getResources().getString(R.string.sure_logout))
                    .setPositiveButton(getResources().getString(R.string.yes), (dialog, which) -> {
                        Intent logoutIntent = new Intent(Overview.this, MainActivity.class);
                        startActivity(logoutIntent);
                    });
            builder.setNegativeButton(getResources().getString(R.string.no), (dialog, which) -> dialog.dismiss());
            builder.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * updates the UI of the overview activity whenever the user starts the application
     * and whenever the user adds, edits or deletes a food entry.
     */
    public void updateUI() {
        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        foodViewModel.getAllFoods().observe(this, adapter::setFoods);

        UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
        final FoodDAO foodDAO = userDatabase.foodDAO();
        sumConsumedCalorie = foodDAO.getTotal(LocalDate.now(), LoggedUser.getUserID());
        tvCalorieConsumedNum.setText(String.valueOf(sumConsumedCalorie));

        calorieRequirement = userDatabase.userDao().searchCalorieRequirement(LoggedUser.getUserID());
        tvTotalCalorieRequirement.setText(getResources().getString(R.string.of_total, String.valueOf(calorieRequirement)));

        circularProgressIndicator.setProgress((int) ((sumConsumedCalorie / calorieRequirement) * 100), true);
    }

}
