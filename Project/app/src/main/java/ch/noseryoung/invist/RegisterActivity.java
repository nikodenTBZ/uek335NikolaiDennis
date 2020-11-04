package ch.noseryoung.invist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "ch.noseryoung.invist.RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"Create RegisterActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Add a back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Register");

        Spinner spinner = findViewById(R.id.spinnerCompanyRegister);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this, R.array.companyArray, android.R.layout.simple_spinner_item
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        Button buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerButtonAction();
            }
        });

        //TextView errorMessage = findViewById(R.id.registerErrorTextView);
        //errorMessage.setText("falscher Benutzer");

    }


    private void registerButtonAction() {
        Log.d(TAG,"Back to Login with Register button");
        finish();
    }
}