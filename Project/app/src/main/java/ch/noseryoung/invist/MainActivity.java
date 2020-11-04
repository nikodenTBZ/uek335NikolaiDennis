package ch.noseryoung.invist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.List;

import ch.noseryoung.invist.model.User;
import ch.noseryoung.invist.persistence.AppDatabase;
import ch.noseryoung.invist.persistence.UserDao;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ch.noseryoung.invist.MainActivity";
    private UserDao userDao;

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: MainActivity");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: MainActivity");
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Create MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userDao = AppDatabase.getAppDb(getApplicationContext()).getUserDao();

        //Remove Action Bar
        getSupportActionBar().hide();

        Button buttonLogIn = findViewById(R.id.buttonLogIn);

        TextView textViewRegister = findViewById(R.id.textViewRegisterLink);

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });


        //insertDummy("dennis.miceli@hotmail.ch");

    }

    private void validateFields() {

        TextView emailTextView = (TextView) findViewById(R.id.emailLogin);
        String email = emailTextView.getText().toString();
        TextView passwordTextView = (TextView) findViewById(R.id.passwordLogin);
        String password = passwordTextView.getText().toString();

        if (email.equals("")){
            System.out.println("null");
        }

        if (checkIfEmailIsInDb(email)) {
            if (userDao.getUser(email).getPassword().equals(password)) {
                openHomeActivity();
            } else {
                showErrorLoginMessage();
            }
        } else {
            showErrorLoginMessage();
        }

    }

    private void showErrorLoginMessage() {
        findViewById(R.id.wrongEmailPassword).setVisibility(View.VISIBLE);
    }

    private boolean checkIfEmailIsInDb(String email) {

        return userDao.getUser(email) != null;
    }

    private void insertDummy(String email) {

        if (!checkIfEmailIsInDb(email)) {
            User user = new User(email);
            Log.d(TAG, "Created new User");

            user.setFirstname("Dennis");
            user.setLastname("Miceli");
            user.setPassword("123456");
            Log.d(TAG, "Added name and firstname and Password to User");
            user.setBirthday(new Date());
            Log.d(TAG, "Added Date to User");

            userDao.insertUser(user);
            Log.d(TAG, "Insert User to DB");
        }


        List<User> userList = userDao.getAll();
        Log.d(TAG, "Get all Users from DB");

        for (User userTemp : userList) {
            System.out.println(userTemp.getEmail());
        }
    }

    /**
     * Changes to the HomeActivity
     */
    private void openHomeActivity() {
        Log.i(TAG, "Open the HomeActivity");
        //Change to Home-Activity
        Intent homeActivityIntent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeActivityIntent);
    }

    /**
     * Changes to the RegisterActivity
     */
    private void openRegisterActivity() {
        Log.i(TAG, "Open the RegisterActivity");
        Intent registerActivityIntent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(registerActivityIntent);
    }


    @Override
    protected void onPause() {
        findViewById(R.id.wrongEmailPassword).setVisibility(View.INVISIBLE);
        super.onPause();
    }
}