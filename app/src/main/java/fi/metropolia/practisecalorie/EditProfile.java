package fi.metropolia.practisecalorie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import fi.metropolia.practisecalorie.user.LoggedUser;
import fi.metropolia.practisecalorie.user.User;
import fi.metropolia.practisecalorie.user.UserDatabase;

public class EditProfile extends AppCompatActivity {

    EditText updateFirstName, updateLastName, updatePassword, retypeUpdatePassword;
    TextView updateUsername, updateHeight, updateWeight;
    Button updateProfileBtn, deleteProfileBtn;
    ImageView updateWeightIncrement, updateWeightDecrement;
    String udFirstName, udLastName, udPassword, username;
    int udHeight, udWeight, udCalorieRequirement, fromDbHeight;
    SeekBar updateSeekBarHeight;
    BottomNavigationView bottomNavigationProfile;


    //getting the user's weight from database
    UserDatabase userDatabase = UserDatabase.getUserDatabase(this);
    int initialWeight = userDatabase.userDao().searchWeight(LoggedUser.getUserID());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Objects.requireNonNull(getSupportActionBar()).hide();

        updateFirstName = findViewById(R.id.updateFirstName);
        updateLastName = findViewById(R.id.updateLastName);
        updateUsername = findViewById(R.id.updateUsername);
        updatePassword = findViewById(R.id.updatePassword);
        retypeUpdatePassword = findViewById(R.id.retypeUpdatePassword);
        updateWeight = findViewById(R.id.updateWeight);
        updateHeight = findViewById(R.id.updateHeight);
        updateProfileBtn = findViewById(R.id.updateProfileBtn);
        updateWeightIncrement = findViewById(R.id.updateWeightIncrement);
        updateWeightDecrement = findViewById(R.id.updateWeightDecrement);
        updateSeekBarHeight = findViewById(R.id.updateSeekBarHeight);
        deleteProfileBtn = findViewById(R.id.deleteProfile);
        bottomNavigationProfile = findViewById(R.id.bottomNavigationProfile);

        bottomNavigationProfile = findViewById(R.id.bottomNavigationProfile);
        bottomNavigationProfile.setSelectedItemId(R.id.profile);


        UserDatabase userDB = UserDatabase.getUserDatabase(getApplicationContext());

        //setting the initial values of user's parameters in the corresponding views
        updateFirstName.setText(userDB.userDao().searchFirstName(LoggedUser.getUserID()));
        updateLastName.setText(userDB.userDao().searchLastName(LoggedUser.getUserID()));
        updateUsername.setText(userDB.userDao().searchUserName(LoggedUser.getUserID()));
        updateHeight.setText(String.valueOf(userDB.userDao().searchHeight(LoggedUser.getUserID())));
        updateWeight.setText(String.valueOf(userDB.userDao().searchWeight(LoggedUser.getUserID())));
        updatePassword.setText(userDB.userDao().searchPassword(LoggedUser.getUserID()));
        retypeUpdatePassword.setText(userDB.userDao().searchPassword(LoggedUser.getUserID()));

        //getting the user's age, userId and gender from the database
        int age = userDB.userDao().age(LoggedUser.getUserID());
        int userId = userDB.userDao().id(LoggedUser.getUserID());
        String gender = userDB.userDao().searchGender(LoggedUser.getUserID());
        username = userDB.userDao().searchUserName(LoggedUser.getUserID());

        //seekBar listener for the height
        updateSeekBarHeight.setMax(300);
        //setting the progress bar at initial height from database
        updateSeekBarHeight.setProgress(userDB.userDao().searchHeight(LoggedUser.getUserID()));
        fromDbHeight = userDB.userDao().searchHeight(LoggedUser.getUserID());
        updateSeekBarHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fromDbHeight = progress;
                updateHeight.setText(String.valueOf(fromDbHeight));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // On-click listener for image buttons that increases the weight
        updateWeightIncrement.setOnClickListener(v -> {
            initialWeight = initialWeight + 1;
            updateWeight.setText(String.valueOf(initialWeight));
        });

        // On-click listener for image buttons that decreases the weight
        updateWeightDecrement.setOnClickListener(v -> {
            initialWeight = initialWeight - 1;
            updateWeight.setText(String.valueOf(initialWeight));
        });

        // On-click listener for update button
        updateProfileBtn.setOnClickListener(v -> {
            //checking whether user has input all the parameters or not
            if (updateFirstName.getText().toString().trim().isEmpty() ||
                    updateLastName.getText().toString().trim().isEmpty() ||
                    updatePassword.getText().toString().trim().isEmpty() ||
                    retypeUpdatePassword.getText().toString().trim().isEmpty() ||
                    updateHeight.getText().toString().trim().isEmpty() ||
                    updateWeight.getText().toString().trim().isEmpty()) {

                Toast.makeText(getApplicationContext(), getResources().getString(R.string.fields_required), Toast.LENGTH_SHORT).show();
                return;
                //checking if both passwords match
            } else if (updatePassword.getText().toString().trim().isEmpty() !=
                    retypeUpdatePassword.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.values_less_than_zero), Toast.LENGTH_SHORT).show();
                return;
                //checking if both heights and weights are not set to zero or less
            } else if ((Double.parseDouble(updateHeight.getText().toString()) <= 0) || Double.parseDouble(updateWeight.getText().toString()) <= 0) {
                Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                return;
            } else {
                udFirstName = updateFirstName.getText().toString().trim();
                udLastName = updateLastName.getText().toString().trim();
                udPassword = updatePassword.getText().toString().trim();
                udHeight = Integer.parseInt(updateHeight.getText().toString());
                udWeight = Integer.parseInt(updateWeight.getText().toString());
            }

            //updating calorie requirement based on gender
            if (gender.equals(getResources().getString(R.string.male))) {
                udCalorieRequirement = (int) Math.ceil((1.2 * (66 + (6.3 * (udWeight * 2.20462)) +
                        (12.9 * (udHeight * 0.393701)) - (6.8 * age))));

            } else {
                udCalorieRequirement = (int) Math.ceil((1.2 * (655 + (4.3 * (udWeight * 2.20462) +
                        (4.7 * (udHeight * 0.393701)) - (4.7 * age)))));
            }

            //updating user in the database
            UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
            User user = new User(userId, udFirstName, udLastName, username, udPassword, gender, age,
                    udWeight, udHeight, udCalorieRequirement);
            userDatabase.userDao().update(user);
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.successfully_updated), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(EditProfile.this, Overview.class);
            startActivity(intent);

        });

        //click listener on the delete button
        deleteProfileBtn.setOnClickListener(v -> {
            //making sure that the user wants to delete the profile via alert dialog box
            AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
            builder.setIcon(R.drawable.ic_warninig)
                    .setCancelable(false)
                    .setTitle(getResources().getString(R.string.Delete_profile))
                    .setMessage(getResources().getString(R.string.confirm_delete_profile))
                    //when user selects yes option
                    .setPositiveButton(getResources().getString(R.string.yes), (dialog, which) -> {
                        UserDatabase deleteUser = UserDatabase.getUserDatabase(getApplicationContext());
                        User user = new User(userId, udFirstName, udLastName, username, udPassword,
                                gender, age, udWeight, udHeight, udCalorieRequirement);
                        deleteUser.userDao().delete(user);
                        deleteUser.foodDAO().deleteFoodALso(LoggedUser.getUserID());
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.successfully_deleted), Toast.LENGTH_SHORT).show();

                        Intent deleteIntent = new Intent(EditProfile.this, MainActivity.class);
                        startActivity(deleteIntent);
                    });
            //when user selects no
            builder.setNegativeButton(getResources().getString(R.string.no), (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        bottomNavigationProfile.setOnItemSelectedListener(item -> {
            //To click on Profile, the profile activity will open
            if (item.getItemId() == R.id.profile) {
                startActivity(new Intent(getApplicationContext(),
                        EditProfile.class));
                overridePendingTransition(0, 0);

                // To Click on home, it will stay in overview activity.
            } else if (item.getItemId() == R.id.home) {
                startActivity(new Intent(getApplicationContext(),
                        Overview.class));
                overridePendingTransition(0, 0);

                //To click on history it will go to history activity
            } else {
                startActivity(new Intent(getApplicationContext(),
                        History.class));
                overridePendingTransition(0, 0);

            }
            return true;
        });


    }
}