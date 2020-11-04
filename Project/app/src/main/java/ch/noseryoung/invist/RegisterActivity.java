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


import org.w3c.dom.Text;

import ch.noseryoung.invist.persistence.AppDatabase;
import ch.noseryoung.invist.persistence.UserDao;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "ch.noseryoung.invist.RegisterActivity";
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Create RegisterActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userDao = AppDatabase.getAppDb(getApplicationContext()).getUserDao();
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
                EditText email = findViewById(R.id.textviewEmailRegister);
                String sEmail = email.getText().toString();
                if(!AreAllFieldsFilled()){
                    TextView fieldsEmpty = findViewById(R.id.registerErrorTextView);
                    fieldsEmpty.setText(R.string.notAllFieldsFilledError);
                } else if (!checkIfEmailisUnique(sEmail)){
                    TextView fieldsEmpty = findViewById(R.id.registerErrorTextView);
                    fieldsEmpty.setText(R.string.notAllFieldsFilledError);
                }
                registerButtonAction();
            }
        });

        //TextView errorMessage = findViewById(R.id.registerErrorTextView);
        //errorMessage.setText("falscher Benutzer");
    }

    public boolean AreAllFieldsFilled() {
        EditText firstName = findViewById(R.id.textviewFirstnameRegister);
        EditText lastName = findViewById(R.id.textviewLastnameRegister);
        EditText email = findViewById(R.id.textviewEmailRegister);
        EditText password = findViewById(R.id.textviewPasswordRegister);
        EditText birthday = findViewById(R.id.textviewBirthdayRegister);
        Spinner company = findViewById(R.id.spinnerCompanyRegister);
        EditText phoneNumber = findViewById(R.id.textviewPhoneNumberRegister);
        EditText adress = findViewById(R.id.textviewAdressRegister);
        EditText city = findViewById(R.id.textviewCityRegister);
        EditText postcode = findViewById(R.id.textviewPostCodeRegister);

        String sFirstName = firstName.getText().toString();
        String sLastName = lastName.getText().toString();
        String sEmail = email.getText().toString();
        String sPassword = password.getText().toString();
        String sBirthday = birthday.getText().toString();
        String sCompany = company.getSelectedItem().toString();
        String sPhoneNumber = phoneNumber.getText().toString();
        String sAdress = adress.getText().toString();
        String sCity = city.getText().toString();
        String sPostcode = postcode.getText().toString();

        //checks if every Field isnt empty then return true
        return !sFirstName.matches("") && !sLastName.matches("") && !sEmail.matches("") && !sPassword.matches("") && !sBirthday.matches("") &&
                !sCompany.matches("") && !sPhoneNumber.matches("") && !sAdress.matches("") && !sCity.matches("") && !sPostcode.matches("");

    }

    public boolean checkIfEmailisUnique(String email) {
        return userDao.getUser(email) == null;
    }

    private void registerButtonAction() {
        Log.d(TAG, "Back to Login with Register button");
        finish();
    }
}