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
                validateFields();
            }
        });

        insertDummyDennis("dennis.miceli@hotmail.ch");
        insertDummyNikolai("nikolai.schunk@gmail.com");


    }

    private void validateFields() {

        TextView emailTextView = (TextView) findViewById(R.id.emailLogin);
        String email = emailTextView.getText().toString();
        TextView passwordTextView = (TextView) findViewById(R.id.passwordLogin);
        String password = passwordTextView.getText().toString();

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

    public void insertDummyDennis(String email) {
        if (!checkIfEmailIsInDb(email)) {

            //================================================//
            User userDennis = new User("dennis.miceli@hotmail.ch");

            Log.d(TAG, "Created new User");
            //================================================//
            userDennis.setFirstname("Dennis");
            userDennis.setLastname("Miceli");
            Log.d(TAG, "Added First and Lastname to User");
            //================================================//
            userDennis.setBirthday(new Date());
            Log.d(TAG, "Added Date to User");
            //================================================//
            userDennis.setPassword("123456");
            Log.d(TAG, "Added Passwort (In String)");
            //================================================//
            userDao.insertUser(userDennis);
            //================================================//
        }
    }

    public void insertDummyNikolai(String email) {
        if (!checkIfEmailIsInDb(email)) {
            //================================================//
            User userNikolai = new User("nikolai.schunk@gmail.com");
            Log.d(TAG, "Created new User");
            //================================================//
            userNikolai.setFirstname("Nikolai");
            userNikolai.setLastname("Schunk");
            Log.d(TAG, "Added First and Lastname to user");
            //================================================//
            userNikolai.setBirthday(new Date(2003, 0, 29));
            Log.d(TAG, "Added Date to User");
            //================================================//
            userNikolai.setPassword("ABC123");
            Log.d(TAG, "Added Passwort (In String)");
            //================================================//
            userNikolai.setCompany("Accenture");
            Log.d(TAG, "Added Company");
            //================================================//
            userNikolai.setPhoneNumber("078 406 73 65");
            Log.d(TAG, "Added Phone Number");
            //================================================//
            userNikolai.setAddress("Hohenbühlstrasse 8");
            Log.d(TAG, "Added Adress");
            //================================================//
            userNikolai.setCity("Zürich");
            Log.d(TAG, "Added City");
            //================================================//
            userNikolai.setPostcode(8032);
            Log.d(TAG, "Added PostCode");
            //================================================//
            userDao.insertUser(userNikolai);
            Log.d(TAG, "Insert User to DB");
            //================================================//
            getAllUserFromDB();
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