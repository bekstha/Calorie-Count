package fi.metropolia.practisecalorie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import fi.metropolia.practisecalorie.user.LoggedUser;
import fi.metropolia.practisecalorie.user.User;
import fi.metropolia.practisecalorie.user.UserDao;
import fi.metropolia.practisecalorie.user.UserDatabase;

/**
 * Main activity of the application where the user can login, sign up or search for their forgotten
 * password
 *
 * @link :-  https://www.youtube.com/playlist?list=PLrnPJCHvNZuDihTpkRs6SpZhqgBqPU118
 * @link :-  https://www.youtube.com/watch?v=UydBl58KJZQ
 * @link :- https://www.youtube.com/watch?v=MXDlY0n6mkc&t=254s
 * @link :- https://youtu.be/JjfSjMs0ImQ
 */
public class MainActivity extends AppCompatActivity {

    Button  loginBtn;
    TextView create, forgotPassword;
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
        forgotPassword = findViewById(R.id.forgotPassword);

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
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.fields_required), Toast.LENGTH_SHORT).show();
            }else{
                UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                final UserDao userDao = userDatabase.userDao();
                User user = userDao.login(userName,password);

                //checking if the user exists in the database
                if(null == user){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid), Toast.LENGTH_SHORT).show();
                } else {
                    LoggedUser loggedUser  = LoggedUser.getInstance();
                    loggedUser.setUserID(user.getId());

                    Intent loginIntent = new Intent(MainActivity.this, Overview.class);
                    startActivity(loginIntent);
                }
            }
        });

        //resetting password
        forgotPassword.setOnClickListener(v -> {
            Intent resetPasswordIntent = new Intent(MainActivity.this, ResetPassword.class);
            startActivity(resetPasswordIntent);
        });

    }


    //calling onBackPressed method when user click back on the main activity and asking if user wants
    //to log out of the application

    /**
     * method when user presses back on the main activity is shown an alert dialog box if the user
     * wants to exit the application
     */
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.exit))
                .setMessage(getResources().getString(R.string.sure_exit))
                .setNegativeButton(getResources().getString(R.string.no), null)
                .setPositiveButton(getResources().getString(R.string.yes), (arg0, arg1) -> MainActivity.super.onBackPressed()).create().show();
    }
}