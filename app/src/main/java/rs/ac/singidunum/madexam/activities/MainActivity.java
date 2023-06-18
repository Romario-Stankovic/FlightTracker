package rs.ac.singidunum.madexam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import rs.ac.singidunum.madexam.R;

public class MainActivity extends AppCompatActivity {

    private void openLoginActivity() {

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(intent);
        this.finish();

    }

    private void logout() {

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("preferences", MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("userId", -1);
        editor.apply();

        Toast.makeText(this, "You have logged out", Toast.LENGTH_SHORT).show();

        openLoginActivity();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener((menuItem) -> {
            if(menuItem.getItemId() == R.id.profileMenuItem) {

            }

            if(menuItem.getItemId() == R.id.logoutMenuItem) {
                logout();
            }

            return true;
        });
    }

}