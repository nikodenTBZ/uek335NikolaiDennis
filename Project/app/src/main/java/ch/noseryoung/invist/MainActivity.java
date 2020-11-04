package ch.noseryoung.invist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import ch.noseryoung.invist.model.User;
import ch.noseryoung.invist.persistence.AppDatabase;
import ch.noseryoung.invist.persistence.UserDao;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ch.noseryoung.invist.MainActivity";
    private UserDao userDao;

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
                openHomeActivity();
            }
        });

        insertDummy("dennis.miceli@hotmail.ch");
        insertDummy("nikolai.schunk@gmail.com");

    }

    public boolean checkIfEmailisUnique(String email) {
        return userDao.getUser(email) == null;
    }

    public void insertDummy(String email) {
        if (checkIfEmailisUnique(email)) {

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

            //================================================//
            User userNikolai = new User("nikolai.schunk@gmail.com");
            Log.d(TAG, "Created new User");
            //================================================//
            userNikolai.setFirstname("Nikolai");
            userNikolai.setLastname("Schunk");
            Log.d(TAG, "Added First and Lastname to user");
            //================================================//
            userNikolai.setBirthday(new Date(2003, 11, 29));
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
            userDao.insertUser(userDennis);
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


}