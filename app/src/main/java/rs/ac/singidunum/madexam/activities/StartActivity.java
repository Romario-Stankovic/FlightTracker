package rs.ac.singidunum.madexam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import rs.ac.singidunum.madexam.R;
import rs.ac.singidunum.madexam.database.UserDatabase;
import rs.ac.singidunum.madexam.database.models.UserModel;

public class StartActivity extends AppCompatActivity {

    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(intent);
        this.finish();
    }

    private void openMainActivity(UserModel user) {
        Intent intent = new Intent(this, MainActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable("user", user);
        intent.putExtras(extras);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("preferences", MODE_PRIVATE);

        int userId = prefs.getInt("userId", -1);

        UserDatabase db = new UserDatabase(this);
        UserModel user = db.findUserByID(userId);

        if(user == null) {
            openLoginActivity();
        } else {
            Toast.makeText(this, "Logged in as: " + user.getName() + " " + user.getLastName(), Toast.LENGTH_SHORT).show();
            openMainActivity(user);
        }


    }
}