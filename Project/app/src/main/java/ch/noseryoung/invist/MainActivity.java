package ch.noseryoung.invist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.mindrot.jbcrypt.BCrypt;

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
        //getAllUserFromDB();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Create MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userDao = AppDatabase.getAppDb(getApplicationContext()).getUserDao();

        //Open the HomeActivity if the User is still logged in
        SharedPreferences invistPrefs = getSharedPreferences("invistPrefs", MODE_PRIVATE);
        if (invistPrefs.contains("activeUser")) {
            openHomeActivity();
        }


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

        //Method to fill in users
        //insertDummyDennis();
    }

    /**
     * Checks if the Email and the Password match with the ones in the database
     */
    private void validateFieldsAndLogin() {

        TextView emailTextView = (TextView) findViewById(R.id.emailLogin);
        String email = emailTextView.getText().toString();
        TextView passwordTextView = (TextView) findViewById(R.id.passwordLogin);
        String password = passwordTextView.getText().toString();

        if (checkIfEmailIsInDb(email)) {
            if (BCrypt.checkpw(password, userDao.getUser(email).getPassword())) {
                addToSharedPreferences(email);
                openHomeActivity();
            } else {
                showErrorLoginMessage();
            }
        } else {
            showErrorLoginMessage();
        }
    }


    /**
     * Adds the email to the Shared Preferences
     *
     * @param email The email to add to the shared preferences
     */
    private void addToSharedPreferences(String email) {
        SharedPreferences invistPrefs = getSharedPreferences("invistPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = invistPrefs.edit();
        editor.putString("activeUser", email);
        editor.apply();
    }


    /**
     * Shows the Error Message
     */
    private void showErrorLoginMessage() {
        findViewById(R.id.wrongEmailPassword).setVisibility(View.VISIBLE);
    }

    /**
     * Checks if the email is in the database
     *
     * @param email
     * @return a boolean if the email is in the database
     */
    private boolean checkIfEmailIsInDb(String email) {
        return userDao.getUser(email) != null;
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

    /**
     * Fill in a test user
     */
    private void insertDummy() {
        String email = "th3craft3r293@gmail.com";
        if (!checkIfEmailIsInDb(email)) {

            //================================================//
            User userDennis = new User(email, "Dennis", "M", BCrypt.hashpw("123456", BCrypt.gensalt()), new Date(2003, 8, 2), "Accenture", "", "", "", "");

            Log.d(TAG, "Created new User");
            //================================================//

            userDao.insertUser(userDennis);
            Log.d(TAG, "Insert new User to DB");
            //================================================//
        }

        email = "nikolai.schunk@gmail.com";
        if (!checkIfEmailIsInDb(email)) {
            //================================================//
            User userNikolai = new User(email, "Nikolai", "S", BCrypt.hashpw("ABC123", BCrypt.gensalt()), new Date(2003, 10, 29), "Accenture", "", "", "", "");
            Log.d(TAG, "Created new User");
            //================================================//
            userDao.insertUser(userNikolai);
            Log.d(TAG, "Insert User to DB");
            //================================================//
        }
    }


    /**
     * Lists all users from the database
     * Only for testing
     */
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


}