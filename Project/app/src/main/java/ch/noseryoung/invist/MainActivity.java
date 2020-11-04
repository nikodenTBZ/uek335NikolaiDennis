package ch.noseryoung.invist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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
        getAllUserFromDB();
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
                validateFieldsAndLogin();
            }
        });

        insertDummyDennis("dennis.miceli@hotmail.ch");
        insertDummyNikolai("nikolai.schunk@gmail.com");


    }

    private void validateFieldsAndLogin() {

        TextView emailTextView = (TextView) findViewById(R.id.emailLogin);
        String email = emailTextView.getText().toString();
        TextView passwordTextView = (TextView) findViewById(R.id.passwordLogin);
        String password = passwordTextView.getText().toString();

        if (checkIfEmailIsInDb(email)) {
            if (userDao.getUser(email).getPassword().equals(password)) {
                addToSharedPreferences(email);
                openHomeActivity();
            } else {
                showErrorLoginMessage();
            }
        } else {
            showErrorLoginMessage();
        }
    }


    public void addToSharedPreferences(String email){
        SharedPreferences invistPrefs = getSharedPreferences("invistPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = invistPrefs.edit();
        editor.putString("activeUser", email);
        editor.apply();
    }



    private void showErrorLoginMessage() {
        findViewById(R.id.wrongEmailPassword).setVisibility(View.VISIBLE);
    }

    private boolean checkIfEmailIsInDb(String email) {
        return userDao.getUser(email) != null;
    }

    public void insertDummyDennis(String email) {
        if (!checkIfEmailIsInDb(email)) {

            //================================================//
            User userDennis = new User("dennis.miceli@hotmail.ch", "Dennis", "Miceli", "123456", new Date(2003,8,2), "Accenture", "076 429 66 80", "Bahnhofstrasse 15", "Altendorf", "8852");

            Log.d(TAG, "Created new User");
            //================================================//

            userDao.insertUser(userDennis);
            Log.d(TAG, "Insert new User to DB");
            //================================================//
        }
    }

    public void insertDummyNikolai(String email) {
        if (!checkIfEmailIsInDb(email)) {
            //================================================//
            User userNikolai = new User("nikolai.schunk@gmail.com","Nikolai","Schunk","ABC123", new Date(2003, 10, 29),"Accenture", "078 406 73 65", "Hohenbühlstrasse 8", "Zürich", "8032");
            Log.d(TAG, "Created new User");
            //================================================//
            userDao.insertUser(userNikolai);
            Log.d(TAG, "Insert User to DB");
            //================================================//
        }
    }

    private void getAllUserFromDB() {
        List<User> userList = userDao.getAll();
        Log.d(TAG, "Get all Users from DB");

        for (User userTemp : userList) {
            System.out.println(userTemp.getFirstname());
            System.out.println(userTemp.getLastname());
            System.out.println(userTemp.getEmail());
            System.out.println(userTemp.getBirthday());
            System.out.println(userTemp.getCompany());
            System.out.println(userTemp.getAddress());
            System.out.println(userTemp.getCity());
            System.out.println(userTemp.getPostcode());

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

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: MainActivity");
        super.onDestroy();
    }


}