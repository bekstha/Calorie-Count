package fi.metropolia.practisecalorie;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import fi.metropolia.practisecalorie.user.LoggedUser;
import fi.metropolia.practisecalorie.user.User;
import fi.metropolia.practisecalorie.user.UserDatabase;

public class EditProfile extends AppCompatActivity {

    EditText updateFirstName, updateLastName, updatePassword, retypeUpdatePassword;
    TextView  updateUsername,updateHeight,updateWeight;
    Button updateProfileBtn, deleteProfileBtn;
    ImageView updateWeightIncrement, updateWeightDecrement;

    String udFirstName, udLastName, udPassword, username;
    int udHeight, udWeight, udCalorieRequirement, fromDbHeight;

    SeekBar updateSeekBarHeight;

    UserDatabase userDatabase = UserDatabase.getUserDatabase(this);
    int initialWeight = userDatabase.userDao().searchWeight(LoggedUser.getUserID());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().hide();


        updateFirstName = findViewById(R.id.updateFirstName);
        updateLastName = findViewById(R.id.updateLastName);
        updateUsername = findViewById(R.id.updateUsername);
        updatePassword = findViewById(R.id.updatePassword);
        retypeUpdatePassword = findViewById(R.id.retypeUpdatePassword);
//        updateDOB = findViewById(R.id.updateDOB);
        updateWeight = findViewById(R.id.updateWeight);
        updateHeight = findViewById(R.id.updateHeight);
        updateProfileBtn = findViewById(R.id.updateProfileBtn);
        updateWeightIncrement = findViewById(R.id.updateWeightIncrement);
        updateWeightDecrement = findViewById(R.id.updateWeightDecrement);
        updateSeekBarHeight = findViewById(R.id.updateSeekBarHeight);
        deleteProfileBtn = findViewById(R.id.deleteProfile);


        UserDatabase userDB = UserDatabase.getUserDatabase(getApplicationContext());

        updateFirstName.setText(userDB.userDao().searchFirstName(LoggedUser.getUserID()));
        updateLastName.setText(userDB.userDao().searchLastName(LoggedUser.getUserID()));
        updateUsername.setText(userDB.userDao().searchLastName(LoggedUser.getUserID()));
        updateHeight.setText(String.valueOf(userDB.userDao().searchHeight(LoggedUser.getUserID())));
        updateWeight.setText(String.valueOf(userDB.userDao().searchWeight(LoggedUser.getUserID())));
        updatePassword.setText(userDB.userDao().searchPassword(LoggedUser.getUserID()));
        retypeUpdatePassword.setText(userDB.userDao().searchPassword(LoggedUser.getUserID()));


        int age = userDB.userDao().age(LoggedUser.getUserID());
        int userId = userDB.userDao().id(LoggedUser.getUserID());
        String gender = userDB.userDao().searchGender(LoggedUser.getUserID());
        username = userDB.userDao().searchUserName(LoggedUser.getUserID());




        //seekBar listener for the height
        updateSeekBarHeight.setMax(300);
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

        // On-click listener for both image buttons that increases and decreases the weight
        updateWeightIncrement.setOnClickListener(v -> {
                initialWeight = initialWeight +1;
                updateWeight.setText(String.valueOf(initialWeight));
        });

        updateWeightDecrement.setOnClickListener(v -> {
            initialWeight = initialWeight - 1;
            updateWeight.setText(String.valueOf(initialWeight));
        });




        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateFirstName.getText().toString().trim().isEmpty() ||
                        updateLastName.getText().toString().trim().isEmpty() ||
                updatePassword.getText().toString().trim().isEmpty() ||
                retypeUpdatePassword.getText().toString().trim().isEmpty() ||
                        updateHeight.getText().toString().trim().isEmpty() ||
                        updateWeight.getText().toString().trim().isEmpty()){

                    Toast.makeText(getApplicationContext(), "All fields required!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (updatePassword.getText().toString().trim().isEmpty() !=
                        retypeUpdatePassword.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    return;
                } else if ((Double.parseDouble(updateHeight.getText().toString()) <= 0) || Double.parseDouble(updateWeight.getText().toString()) <= 0){
                    Toast.makeText(getApplicationContext(), "Values cannot be less or equal to 0!", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    udFirstName = updateFirstName.getText().toString().trim();
                    udLastName = updateLastName.getText().toString().trim();
                    udPassword = updatePassword.getText().toString().trim();
                    udHeight = Integer.parseInt(updateHeight.getText().toString());
                    udWeight = Integer.parseInt(updateWeight.getText().toString());
                }

                if (gender.equals("Male")) {
                    udCalorieRequirement = (int) Math.ceil((1.2 * (66 + (6.3 * (udWeight * 2.20462)) + (12.9 * (udHeight * 0.393701)) - (6.8 * age))));

                } else{
                    udCalorieRequirement = (int) Math.ceil((1.2 * (655 + (4.3 * (udWeight * 2.20462) + (4.7 * (udHeight * 0.393701)) - (4.7 * age)))));
                }

                UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                User user = new User(userId, udFirstName, udLastName, username, udPassword, gender, age, udWeight, udHeight, udCalorieRequirement);
                userDatabase.userDao().update(user);

                Intent intent = new Intent(EditProfile.this, Overview.class);
                startActivity(intent);

            }


        });


        deleteProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"HIII!", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);

                builder.setIcon(R.drawable.ic_warninig)
                        .setCancelable(false)
                        .setTitle("Delete the profile")
                        .setMessage("Are you sure you want to delete your profile?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserDatabase userDB = UserDatabase.getUserDatabase(getApplicationContext());
                                User user = new User(userId, udFirstName, udLastName, username, udPassword, gender, age, udWeight, udHeight, udCalorieRequirement);
                                userDB.userDao().delete(user);


                                Intent deleteIntent = new Intent(EditProfile.this, MainActivity.class);
                                startActivity(deleteIntent);
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
            }
        });







    }
}