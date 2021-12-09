package fi.metropolia.practisecalorie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fi.metropolia.practisecalorie.user.LoggedUser;
import fi.metropolia.practisecalorie.user.User;
import fi.metropolia.practisecalorie.user.UserDao;
import fi.metropolia.practisecalorie.user.UserDatabase;

public class MainActivity extends AppCompatActivity {

    Button createBtn, loginBtn;
    EditText etUserName, etPassword;
//    public static final String FROM_DB_CALORIE_REQUIREMENT = "Calorie Requirement";

    public static final String TAG = "SignIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);


        createBtn = findViewById(R.id.createBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createIntent = new Intent(MainActivity.this, Credentials.class);
                startActivity(createIntent);
            }
        });

        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etUserName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (userName.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "All fields required!", Toast.LENGTH_SHORT).show();
                }else{
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    final UserDao userDao = userDatabase.userDao();
                    User user = userDao.login(userName,password);
                    if(null == user){
                        Toast.makeText(getApplicationContext(), "Invalid credentials!", Toast.LENGTH_SHORT).show();
                    } else {
                        LoggedUser loggedUser  = LoggedUser.getInstance();
                        loggedUser.setUserID(user.getId());
                        Log.d(TAG, "the user are with user id " +LoggedUser.getUserID());

                        Intent loginIntent = new Intent(MainActivity.this, Overview.class);
                        startActivity(loginIntent);
                    }
                }
            }
        });
    }
}