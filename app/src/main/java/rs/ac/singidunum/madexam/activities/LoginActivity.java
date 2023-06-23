package rs.ac.singidunum.madexam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import rs.ac.singidunum.madexam.R;
import rs.ac.singidunum.madexam.database.UserDatabase;
import rs.ac.singidunum.madexam.database.models.User;

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    Button loginButton;
    Button registerButton;

    UserDatabase userDatabase;

    private void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(intent);
        this.finish();
    }

    private void login() {

        // Get the username and password from the fields
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Find a user by his username
        User user = userDatabase.findUserByUsername(username);

        // If the user does not exist, show a message
        if(user == null) {
            Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show();
            return;
        }

        // If the password does not match, show a message
        if(!user.getPassword().equals(password)) {
            Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get shared preferences
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("preferences", MODE_PRIVATE);

        // Get the editor reference
        SharedPreferences.Editor editor = prefs.edit();

        // Add user's ID to shared preferences
        editor.putInt("userId", user.getId());

        // Apply the changes
        editor.apply();

        // Show login message to the user
        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

        // Open the main activity
        openMainActivity();

    }

    private void init() {

        // Get a reference to the database
        userDatabase = new UserDatabase(this);

        // Get the views
        usernameEditText = findViewById(R.id.a_login_username_editText);
        passwordEditText = findViewById(R.id.a_login_password_editText);
        loginButton = findViewById(R.id.a_login_login_button);
        registerButton = findViewById(R.id.a_login_register_button);

        // Add the click listener to the login button
        loginButton.setOnClickListener((view) -> {
            login();
        });

        // Add the click listener to the register button
        registerButton.setOnClickListener((view) -> {
            openRegisterActivity();
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        // Initialize the component
        init();
    }
}