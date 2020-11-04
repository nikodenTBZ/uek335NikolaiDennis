package ch.noseryoung.invist;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ch.noseryoung.invist.model.User;
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
        getSupportActionBar().setTitle(R.string.register);

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
                validateAndFillInDb();
            }
        });
    }

    public void validateAndFillInDb() {
        EditText firstName = findViewById(R.id.textviewFirstnameRegister);
        EditText lastName = findViewById(R.id.textviewLastnameRegister);
        EditText email = findViewById(R.id.textviewEmailRegister);
        EditText password = findViewById(R.id.textviewPasswordRegister);
        EditText birthday = findViewById(R.id.textviewBirthdayRegister);
        Spinner company = findViewById(R.id.spinnerCompanyRegister);
        EditText phoneNumber = findViewById(R.id.textviewPhoneNumberRegister);
        EditText address = findViewById(R.id.textviewAdressRegister);
        EditText city = findViewById(R.id.textviewCityRegister);
        EditText postcode = findViewById(R.id.textviewPostCodeRegister);

        String sFirstName = firstName.getText().toString();
        String sLastName = lastName.getText().toString();
        String sEmail = email.getText().toString();
        String sPassword = password.getText().toString();
        String sBirthday = birthday.getText().toString();
        String sCompany = company.getSelectedItem().toString();
        String sPhoneNumber = phoneNumber.getText().toString();
        String sAddress = address.getText().toString();
        String sCity = city.getText().toString();
        String sPostcode = postcode.getText().toString();

        if (!AreAllFieldsFilled(sFirstName, sLastName, sEmail, sPassword, sBirthday, sCompany, sPhoneNumber, sAddress, sCity, sPostcode)) {
            TextView fieldsEmpty = findViewById(R.id.registerErrorTextView);
            fieldsEmpty.setText(R.string.notAllFieldsFilledError);
            Log.d(TAG, "ERROR, not all Fields are Filled");
        } else if (checkIfEmailIsInDb(sEmail)) {
            TextView fieldsEmpty = findViewById(R.id.registerErrorTextView);
            fieldsEmpty.setText(R.string.EmailAlreadyExist);
            Log.d(TAG, "ERROR, Email exist already");
        } else {
            //User user = new User()
            registerButtonAction();
            Log.d(TAG, "Registered Successfully");
        }
    }

    public boolean AreAllFieldsFilled(String sFirstName, String sLastName, String sEmail, String sPassword, String sBirthday, String sCompany, String sPhoneNumber, String sAddress, String sCity, String sPostcode) {

        //checks if every Field isnt empty then return true
        return !sFirstName.matches("") && !sLastName.matches("") && !sEmail.matches("") && !sPassword.matches("") && !sBirthday.matches("") &&
                !sCompany.matches("") && !sPhoneNumber.matches("") && !sAddress.matches("") && !sCity.matches("") && !sPostcode.matches("");

    }

    private boolean checkIfEmailIsInDb(String email) {
        return userDao.getUser(email) != null;
    }

    private void registerButtonAction() {
        Log.d(TAG, "Back to Login with Register button");
        finish();
    }

}