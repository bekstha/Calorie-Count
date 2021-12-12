package fi.metropolia.practisecalorie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import fi.metropolia.practisecalorie.user.User;
import fi.metropolia.practisecalorie.user.UserDao;
import fi.metropolia.practisecalorie.user.UserDatabase;

public class Credentials extends AppCompatActivity {

    Button nextBtn;
    EditText etFirstName, etLastName, etUsername, etPassword, etReTypePassword;
    String firstName, lastName, userName, password;

    public static final String EXTRA_FIRST_NAME = "first name";
    public static final String EXTRA_LAST_NAME = "last name";
    public static final String EXTRA_USER_NAME = "user name";
    public static final String EXTRA_PASSWORD = " password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credentials);
        getSupportActionBar().hide();

        nextBtn = findViewById(R.id.nextBtn);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etUsername = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        etReTypePassword = findViewById(R.id.etReTypePassword);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                if (etFirstName.getText().toString().trim().isEmpty() ||
                        etLastName.getText().toString().trim().isEmpty() ||
                        etUsername.getText().toString().trim().isEmpty() ||
                        etPassword.getText().toString().trim().isEmpty() ||
                        etReTypePassword.getText().toString().trim().isEmpty()){

                    Toast.makeText(getApplicationContext(), "All fields required!", Toast.LENGTH_SHORT).show();

                } else if (!etPassword.getText().toString().trim().equals(etReTypePassword.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show();

                }else if (userDatabase.userDao().checkUser(etUsername.getText().toString().trim()) != null) {
                        Toast.makeText(getApplicationContext(), "The username already exists!", Toast.LENGTH_SHORT).show();
                }else{
                    firstName = etFirstName.getText().toString().trim();
                    lastName = etLastName.getText().toString().trim();
                    userName = etUsername.getText().toString().trim();
                    password = etPassword.getText().toString().trim();

                    Intent intent = new Intent(Credentials.this, Profile.class);
                    intent.putExtra(EXTRA_FIRST_NAME, firstName);
                    intent.putExtra(EXTRA_LAST_NAME, lastName);
                    intent.putExtra(EXTRA_USER_NAME,lastName);
                    intent.putExtra(EXTRA_PASSWORD,password);

                    startActivity(intent);
                }
            }
        });
    }
}