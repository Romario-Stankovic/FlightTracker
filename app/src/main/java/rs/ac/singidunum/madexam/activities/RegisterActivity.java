package rs.ac.singidunum.madexam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import rs.ac.singidunum.madexam.R;
import rs.ac.singidunum.madexam.database.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText lastNameEditText;
    EditText usernameEditText;
    EditText passwordEditText;
    RadioGroup genderRadioGroup;
    Button registerButton;
    Button backButton;

    private void goBack() {

        finish();

    }

    private void register() {

        DatabaseHelper db = new DatabaseHelper(this);

        String name = nameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String dateOfBirth = "";
        String gender = ((RadioButton)findViewById(genderRadioGroup.getCheckedRadioButtonId())).getText().toString().trim();

        boolean result = db.createUser(name, lastName, username, password, dateOfBirth, gender);

        if(result == false) {
            Toast.makeText(this, "Failed to register", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Registration successful", Toast.LENGTH_LONG).show();
            finish();
        }

    }

    private void init() {

        nameEditText = findViewById(R.id.nameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);

        registerButton = findViewById(R.id.registerButton);
        backButton = findViewById(R.id.backButton);

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