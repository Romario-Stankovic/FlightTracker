package rs.ac.singidunum.madexam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import rs.ac.singidunum.madexam.R;
import rs.ac.singidunum.madexam.database.UserDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText lastNameEditText;
    EditText usernameEditText;
    EditText passwordEditText;
    EditText dateOfBirthEditText;
    RadioGroup genderRadioGroup;
    Button registerButton;
    Button backButton;

    Calendar dateOfBirthCalendar = Calendar.getInstance();
    UserDatabase userDatabase;

    private void goBack() {
        finish();
    }

    private void register() {

        // Get values from the fields
        String name = nameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Convert the date to a ISO date format
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateOfBirth = format.format(dateOfBirthCalendar.getTime());
        String gender = ((RadioButton)findViewById(genderRadioGroup.getCheckedRadioButtonId())).getText().toString().trim();

        // Save the user to the database
        boolean result = userDatabase.createUser(name, lastName, username, password, dateOfBirth, gender);

        // Show appropriate message based on the save result
        if(!result) {
            Toast.makeText(this, "Failed to register", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void init() {

        // Get user database
        userDatabase = new UserDatabase(this);

        // Get references to the View
        nameEditText = findViewById(R.id.a_register_name_editText);
        lastNameEditText = findViewById(R.id.a_register_lastName_editText);
        usernameEditText = findViewById(R.id.a_register_username_editText);
        passwordEditText = findViewById(R.id.a_register_password_editText);
        dateOfBirthEditText = findViewById(R.id.a_register_dateOfBirth_editText);
        genderRadioGroup = findViewById(R.id.a_register_gender_radioGroup);

        registerButton = findViewById(R.id.a_register_register_button);
        backButton = findViewById(R.id.a_register_back_button);

        // Create a new datePicker listener
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Get the values from the dataPicker and set them on the Calender instance
                dateOfBirthCalendar.set(Calendar.YEAR, year);
                dateOfBirthCalendar.set(Calendar.MONTH, month);
                dateOfBirthCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                // Format the date
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                // Set the text in the date field
                dateOfBirthEditText.setText(dateFormat.format(dateOfBirthCalendar.getTime()));
            }
        };

        // Add onClick listener to the date of birth field
        dateOfBirthEditText.setOnClickListener((view) -> {
            new DatePickerDialog(this, onDateSetListener, dateOfBirthCalendar.get(Calendar.YEAR), dateOfBirthCalendar.get(Calendar.MONTH), dateOfBirthCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        // Add onClick listener to the register button
        registerButton.setOnClickListener((view) -> {
            register();
        });

        // Add onClick listener to the login button
        backButton.setOnClickListener((view) -> {
            goBack();
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        // Initialize the activity
        init();
    }
}