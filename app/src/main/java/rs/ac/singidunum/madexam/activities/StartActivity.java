package rs.ac.singidunum.madexam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import rs.ac.singidunum.madexam.R;
import rs.ac.singidunum.madexam.database.UserDatabase;
import rs.ac.singidunum.madexam.database.models.User;

public class StartActivity extends AppCompatActivity {

    UserDatabase userDatabase;
    // Replace this activity with the login activity
    private void openLoginActivity() {
        // Create an intent
        Intent intent = new Intent(this, LoginActivity.class);
        // Set intent flags
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        // Start the activity
        startActivity(intent);
        // Finish this activity
        this.finish();
    }

    // Replace this activity with the main activity
    private void openMainActivity() {
        // Create an intent
        Intent intent = new Intent(this, MainActivity.class);
        // Set intent flags
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        // Start the activity
        startActivity(intent);
        // Finish this activity
        this.finish();
    }

    private void init() {

        // Get the shared preferences instance
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("preferences", MODE_PRIVATE);

        // Get the userId from shared preferences or -1 if none exists
        int userId = prefs.getInt("userId", -1);

        // Get the user database instance
        userDatabase = new UserDatabase(this);
        // Get the user by his ID from the database
        User user = userDatabase.findUserByID(userId);

        if(user == null) {
            // If there is no user, open the login Activity
            openLoginActivity();
        } else {
            // Else, display the logged in users full name and open the main activity
            Toast.makeText(this, "Logged in as: " + user.getName() + " " + user.getLastName(), Toast.LENGTH_SHORT).show();
            openMainActivity();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        // Disable the top action bar
        getSupportActionBar().hide();

        // Initialize the component
        init();


    }
}