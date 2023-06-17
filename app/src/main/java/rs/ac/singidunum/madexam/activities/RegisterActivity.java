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
import java.time.Instant;
import java.util.Calendar;
import java.util.Locale;

import rs.ac.singidunum.madexam.R;
import rs.ac.singidunum.madexam.database.DatabaseHelper;
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

    private void goBack() {
        finish();
    }

    private void register() {

        UserDatabase db = new UserDatabase(this);

        String name = nameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateOfBirth = format.format(dateOfBirthCalendar.getTime());
        String gender = ((RadioButton)findViewById(genderRadioGroup.getCheckedRadioButtonId())).getText().toString().trim();

        boolean result = db.createUser(name, lastName, username, password, dateOfBirth, gender);

        if(result == false) {
            Toast.makeText(this, "Failed to register", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void init() {

        nameEditText = findViewById(R.id.nameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        dateOfBirthEditText = findViewById(R.id.dateOfBirthEditText);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);

        registerButton = findViewById(R.id.registerButton);
        backButton = findViewById(R.id.backButton);

        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateOfBirthCalendar.set(Calendar.YEAR, year);
                dateOfBirthCalendar.set(Calendar.MONTH, month);
                dateOfBirthCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                dateOfBirthEditText.setText(dateFormat.format(dateOfBirthCalendar.getTime()));
            }
        };

        dateOfBirthEditText.setOnClickListener((view) -> {
            new DatePickerDialog(this, onDateSetListener, dateOfBirthCalendar.get(Calendar.YEAR), dateOfBirthCalendar.get(Calendar.MONTH), dateOfBirthCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        registerButton.setOnClickListener((view) -> {
            register();
        });

        backButton.setOnClickListener((view) -> {
            goBack();
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        init();
    }
}