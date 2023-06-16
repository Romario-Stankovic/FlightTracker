package rs.ac.singidunum.madexam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import rs.ac.singidunum.madexam.R;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    Button registerButton;

    private void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void init() {

        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener((view) -> {
            openRegisterActivity();
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        init();
    }
}