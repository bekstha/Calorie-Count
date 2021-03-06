package fi.metropolia.practisecalorie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import fi.metropolia.practisecalorie.user.UserDatabase;


/**
 * Activity where user can find their password in case they forget their password
 */
public class ResetPassword extends AppCompatActivity {
    EditText resetFirstName, resetLastName, resetUserName;
    Button revealBtn;
    TextView revealPassword, goBackToLogin;
    String checkFirstName, checkLastName, checkUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Objects.requireNonNull(getSupportActionBar()).hide();

        resetFirstName = findViewById(R.id.resetFirstName);
        resetLastName = findViewById(R.id.resetLastName);
        resetUserName = findViewById(R.id.resetUserName);
        revealBtn = findViewById(R.id.revealBtn);
        revealPassword = findViewById(R.id.revealPassword);
        goBackToLogin = findViewById(R.id.goBackLogin);

        //reveal Button click listener
        revealBtn.setOnClickListener(v -> {
            UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());

            //checking if user has filled all the required fields
            if (resetUserName.getText().toString().trim().isEmpty() ||
                    resetFirstName.getText().toString().trim().isEmpty() ||
                    resetLastName.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.fields_required), Toast.LENGTH_SHORT).show();
                return;
            } else {
                //checking if a matching user exists in the database
                checkFirstName = resetFirstName.getText().toString().trim();
                checkLastName = resetLastName.getText().toString().trim();
                checkUserName = resetUserName.getText().toString().trim();
            }

            //if user exists the program reveals their saved password
            if (userDatabase.userDao().checkPassword(checkUserName, checkFirstName, checkLastName) != null) {
                revealPassword.setText(userDatabase.userDao().checkPassword(checkUserName, checkFirstName, checkLastName));
            } else {
                // if no such user exists the program responds no user found
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_user), Toast.LENGTH_SHORT).show();
            }
        });


        // takes user back to the login screen
        goBackToLogin.setOnClickListener(v -> {
            Intent goBackToLogin = new Intent(ResetPassword.this, MainActivity.class);
            startActivity(goBackToLogin);
        });

    }
}