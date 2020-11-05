package ch.noseryoung.invist;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.apache.commons.validator.routines.EmailValidator;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Arrays;
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

    public void validateAndFillInDb() {
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

        if (!AreAllFieldsFilled(sFirstName, sLastName, sEmail, sPassword, sBirthday, sCompany)) {
            errorTextView.setText(R.string.notAllFieldsFilledError);
            Log.d(TAG, "ERROR, not all Fields are Filled");
        } else if (checkIfEmailIsInDb(sEmail)) {
            errorTextView.setText(R.string.EmailAlreadyExist);
            Log.d(TAG, "ERROR, Email exist already");
        } else {
            if (sFirstName.matches("[A-Za-z- äöü]{2,50}")) {
                if (sLastName.matches("[A-Za-z- äöü]{2,50}")) {
                    if (isValidEmail(sEmail) && sEmail.matches("[2,100]")) {
                        if (sPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.,])[A-Za-z\\d@$!%*?&.,]{6,}$")) {
                            if (sBirthday.matches("[0-9]{2}.[0-9]{2}.[0-9]{4}")) {
                                if (sPhoneNumber.matches("\\+?[0-9 ]{10,}")) {
                                    if (sAddress.matches("([A-Za-z- äöü]+[0-9a-z]{1,4}){255}")) {
                                        if (sCity.matches("[A-Za-z äöü]+")) {
                                            if (sPostcode.matches("[0-9]{4}")) {

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


    public boolean AreAllFieldsFilled(String sFirstName, String sLastName, String
            sEmail, String sPassword, String sBirthday, String sCompany) {

        //checks if every Field isnt empty then return true
        return !sFirstName.matches("") && !sLastName.matches("") && !sEmail.matches("") && !sPassword.matches("") && !sBirthday.matches("") &&
                !sCompany.matches("");

    }

    public boolean isValidEmail(String email) {
        // create the EmailValidator instance
        EmailValidator validator = EmailValidator.getInstance();

        // check for valid email addresses using isValid method
        return validator.isValid(email);
    }

    public Date splitBirthdayString(String birthday) {
        System.out.println(birthday);
        String[] splitBday = birthday.split("\\.");
        System.out.println(Arrays.toString(splitBday));
        int day = Integer.parseInt(splitBday[0]);
        int month = Integer.parseInt(splitBday[1]);
        int year = Integer.parseInt(splitBday[2]);
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