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

import org.apache.commons.validator.routines.EmailValidator;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;

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

    private void validateAndFillInDb() {
        TextView errorTextView = findViewById(R.id.registerErrorTextView);

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

        if (!areAllFieldsFilled(sFirstName, sLastName, sEmail, sPassword, sBirthday, sCompany)) {
            errorTextView.setText(R.string.notAllFieldsFilledError);
            Log.d(TAG, "ERROR, not all Fields are Filled");
        } else if (checkIfEmailIsInDb(sEmail)) {
            errorTextView.setText(R.string.EmailAlreadyExist);
            Log.d(TAG, "ERROR, Email exist already");
        } else {
            if (sFirstName.matches("[A-Za-z- äöü]{2,50}")) {
                if (sLastName.matches("[A-Za-z- äöü]{2,50}")) {
                    if (isValidEmail(sEmail) && sEmail.length() <= 100) {
                        if (sPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.,])[A-Za-z\\d@$!%*?&.,]{6,}$")) {
                            if (sBirthday.matches("[0-9]{2}.[0-9]{2}.[0-9]{4}") || sBirthday.matches("[0-9]{8}")) {
                                if (sPhoneNumber.matches("\\+?[0-9 ]{10,}") || sPhoneNumber.length() == 0) {
                                    if (sAddress.matches("([A-Za-z- äöü]+[0-9a-z]{1,4}){1,255}") || sAddress.length() == 0) {
                                        if (sCity.matches("[A-Za-z äöü]{1,50}") || sCity.length() == 0) {
                                            if (sPostcode.matches("[0-9]{4}") || sCity.length() == 0) {

                                                String hashedPw = BCrypt.hashpw(sPassword, BCrypt.gensalt());

                                                Date actualBirthday = splitBirthdayString(sBirthday);
                                                User user = new User(sEmail, sFirstName, sLastName, hashedPw, actualBirthday, sCompany, sPhoneNumber, sAddress, sCity, sPostcode);
                                                userDao.insertUser(user);
                                                registerButtonAction();
                                                Log.d(TAG, "Registered Successfully");

                                            } else {
                                                errorTextView.setText(R.string.invalidPostCode);
                                                Log.d(TAG, "ERROR, invalid Postcode");
                                            }
                                        } else {
                                            errorTextView.setText(R.string.invalidCity);
                                            Log.d(TAG, "ERROR, invalid City");
                                        }
                                    } else {
                                        errorTextView.setText(R.string.invalidAddress);
                                        Log.d(TAG, "ERROR, invalid Address");
                                    }
                                } else {
                                    errorTextView.setText(R.string.invalidPhoneNumber);
                                    Log.d(TAG, "ERROR, invalid PhoneNumber");
                                }
                            } else {
                                errorTextView.setText(R.string.invalidBirthday);
                                Log.d(TAG, "ERROR, invalid Birthday");
                            }
                        } else {
                            errorTextView.setText(R.string.invalidPassword);
                            Log.d(TAG, "ERROR, invalid Password");
                        }
                    } else {
                        errorTextView.setText(R.string.invalidEmail);
                        Log.d(TAG, "ERROR, invalid Email");
                    }
                } else {
                    errorTextView.setText(R.string.invalidLastname);
                    Log.d(TAG, "ERROR, invalid Lastname");
                }
            } else {
                errorTextView.setText(R.string.invalidFirstname);
                Log.d(TAG, "ERROR, invalid Firstname");
            }
        }
    }


    private boolean areAllFieldsFilled(String sFirstName, String sLastName, String
            sEmail, String sPassword, String sBirthday, String sCompany) {

        //checks if every Field isnt empty then return true
        return !sFirstName.matches("") && !sLastName.matches("") && !sEmail.matches("") && !sPassword.matches("") && !sBirthday.matches("") &&
                !sCompany.matches("");

    }

    private boolean isValidEmail(String email) {
        // create the EmailValidator instance
        EmailValidator validator = EmailValidator.getInstance();

        // check for valid email addresses using isValid method
        return validator.isValid(email);
    }

    private Date splitBirthdayString(String birthday) {
        Log.d(TAG, "splitBirthdayString: RegisterActivity");
        String[] splitBday = birthday.split("\\.");
        int day;
        int month;
        int year;

        if (splitBday.length == 3){
             day = Integer.parseInt(splitBday[0]);
             month = Integer.parseInt(splitBday[1]);
             year = Integer.parseInt(splitBday[2]);

        } else {
             day = Integer.parseInt(splitBday[0].substring(0,2));
             month = Integer.parseInt(splitBday[0].substring(2,4));
             year = Integer.parseInt(splitBday[0].substring(4,8));
        }
        return new Date(year, month - 1, day);
    }

    private boolean checkIfEmailIsInDb(String email) {
        return userDao.getUser(email) != null;
    }

    private void registerButtonAction() {
        Log.d(TAG, "Back to Login with Register button");
        finish();
    }

}