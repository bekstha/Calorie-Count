package fi.metropolia.practisecalorie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.UUID;

public class Overview extends AppCompatActivity {

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

        findViewById(R.id.addFoodBtn).setOnClickListener(v -> {
            Intent intent = new Intent(Overview.this, FoodItems.class);
            startForResult.launch(intent);
        });


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //complete day button
        findViewById(R.id.completeBtn).setOnClickListener(v -> {
            Intent completedDayIntent = new Intent(Overview.this, CompletedDay.class);
            startActivity(completedDayIntent);
        });





    }
}