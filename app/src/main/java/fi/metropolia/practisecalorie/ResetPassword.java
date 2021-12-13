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

public class ResetPassword extends AppCompatActivity {
    EditText resetFirstName, resetLastName, resetUserName;
    Button resetBtn;
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
        resetBtn = findViewById(R.id.resetBtn);
        revealPassword = findViewById(R.id.revealPassword);
        goBackToLogin = findViewById(R.id.goBackLogin);

        resetBtn.setOnClickListener(v -> {
            UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());

            if (resetUserName.getText().toString().trim().isEmpty() ||
                    resetFirstName.getText().toString().trim().isEmpty() ||
                    resetLastName.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            } else {
                checkFirstName = resetFirstName.getText().toString().trim();
                checkLastName = resetLastName.getText().toString().trim();
                checkUserName = resetUserName.getText().toString().trim();
            }

            if (userDatabase.userDao().checkPassword(checkUserName, checkFirstName, checkLastName) != null) {
                revealPassword.setText(userDatabase.userDao().checkPassword(checkUserName, checkFirstName, checkLastName));
            } else {
                Toast.makeText(getApplicationContext(), "No user found!", Toast.LENGTH_SHORT).show();
            }
        });


        goBackToLogin.setOnClickListener(v -> {
            Intent goBackToLogin = new Intent(ResetPassword.this, MainActivity.class);
            startActivity(goBackToLogin);
        });

    }
}