package fi.metropolia.practisecalorie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import fi.metropolia.practisecalorie.user.LoggedUser;
import fi.metropolia.practisecalorie.user.User;
import fi.metropolia.practisecalorie.user.UserDao;
import fi.metropolia.practisecalorie.user.UserDatabase;

public class MainActivity extends AppCompatActivity {

    Button  loginBtn;
    TextView create;
    EditText etUserName, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        create = findViewById(R.id.tvCreate);
        loginBtn = findViewById(R.id.loginBtn);

        //setting on click listener to take user to another activity to create their profile
        create.setOnClickListener(v -> {
            Intent createIntent = new Intent(MainActivity.this, Credentials.class);
            startActivity(createIntent);
        });

        //setting on click listener to login the user
        loginBtn.setOnClickListener(v -> {
            String userName = etUserName.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            //checking if the user inputs all both their username and password to login
            if (userName.isEmpty() || password.isEmpty()){
                Toast.makeText(getApplicationContext(), "All fields required!", Toast.LENGTH_SHORT).show();
            }else{
                UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                final UserDao userDao = userDatabase.userDao();
                User user = userDao.login(userName,password);

                //checking if the user exists in the database
                if(null == user){
                    Toast.makeText(getApplicationContext(), "Invalid credentials!", Toast.LENGTH_SHORT).show();
                } else {
                    LoggedUser loggedUser  = LoggedUser.getInstance();
                    loggedUser.setUserID(user.getId());

                    Intent loginIntent = new Intent(MainActivity.this, Overview.class);
                    startActivity(loginIntent);
                }
            }
        });
    }
}