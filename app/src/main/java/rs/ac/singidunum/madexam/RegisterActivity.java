package rs.ac.singidunum.madexam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import rs.ac.singidunum.madexam.R;

public class RegisterActivity extends AppCompatActivity {

    private void goBackToLogin() {

        finish();

    }

    private void initUI() {

        Button registerButton = findViewById(R.id.registerButton);
        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener((view) -> {
            goBackToLogin();
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initUI();
    }
}