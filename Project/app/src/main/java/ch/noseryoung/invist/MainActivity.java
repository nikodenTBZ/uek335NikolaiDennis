package ch.noseryoung.invist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ch.noseryoung.invist.MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Create MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    /**
     * Changes to the HomeActivity
     */
    private void openHomeActivity() {
        Log.i(TAG,"Open the HomeActivity");
        //Change to Home-Activity
        Intent homeActivityIntent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeActivityIntent);
    }

    /**
     * Changes to the RegisterActivity
     */
    private void openRegisterActivity() {
        Log.i(TAG,"Open the RegisterActivity");
        Intent registerActivityIntent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(registerActivityIntent);
    }


}