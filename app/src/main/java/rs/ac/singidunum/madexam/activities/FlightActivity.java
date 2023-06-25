package rs.ac.singidunum.madexam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import rs.ac.singidunum.madexam.misc.Environment;
import rs.ac.singidunum.madexam.R;
import rs.ac.singidunum.madexam.database.UserFlightDatabase;
import rs.ac.singidunum.madexam.database.models.UserFlight;

public class FlightActivity extends AppCompatActivity {

    UserFlightDatabase userFlightDatabase;

    TextView flightNumberTextView;
    TextView destinationTextView;
    TextView scheduledAtTextView;
    TextView connectedFlightTextView;
    TextView planeTextView;
    TextView gateTextView;
    TextView terminalTextView;
    ImageView bannerImageView;
    ImageButton saveButton;

    SharedPreferences prefs;

    private void init() {

        // Get the userFlight database
        userFlightDatabase = new UserFlightDatabase(this);

        // Get shared preferences
        prefs = getSharedPreferences("preferences", MODE_PRIVATE);

        // Get the received bundle
        Bundle extras = getIntent().getExtras();

        // Get values from the bundle
        int flightId = extras.getInt("flightId");
        String flightKey = extras.getString("flightKey");
        String flightNumber = extras.getString("flightNumber");
        String destination = extras.getString("destination");
        long scheduledAtMillis = extras.getLong("scheduledAtMillis");
        String connectedFlight = extras.getString("connectedFlight");
        String plane = extras.getString("plane");
        String gate = extras.getString("gate");
        String terminal = extras.getString("terminal");

        // Get view references
        flightNumberTextView = findViewById(R.id.a_flight_flightNumberData_textView);
        destinationTextView = findViewById(R.id.a_flight_destinationData_textView);
        scheduledAtTextView = findViewById(R.id.a_flight_scheduledAtData_textView);
        connectedFlightTextView = findViewById(R.id.a_flight_connectedFlightData_textView);
        planeTextView = findViewById(R.id.a_flight_planeModelData_textView);
        gateTextView = findViewById(R.id.a_flight_gateData_textView);
        terminalTextView = findViewById(R.id.a_flight_terminalData_textView);
        bannerImageView = findViewById(R.id.a_flight_banner_imageView);
        saveButton = findViewById(R.id.a_flight_save_button);

        // Set the action bar title
        getSupportActionBar().setTitle(flightNumber);

        // Set values into the views
        flightNumberTextView.setText(flightKey);
        destinationTextView.setText(destination);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        scheduledAtTextView.setText(dateFormat.format(new Date(scheduledAtMillis)));
        connectedFlightTextView.setText(connectedFlight != null ? connectedFlight : "N/A");
        planeTextView.setText(plane);
        gateTextView.setText(gate);
        terminalTextView.setText(terminal);

        // Get Banner image name (first part of destination name to lower case + .jpg)
        String imageName = "/" + destination.split(" ")[0].toLowerCase() + ".jpg";

        // Load the image into ImageView using Glide
        Glide.with(this)
                .load(Environment.DESTINATION_IMAGE_URL + imageName)
                .placeholder(R.drawable.flight_placeholder)
                .into(bannerImageView);

        // Get the userId
        int userId = prefs.getInt("userId", -1);

        // Check if the flight is starred by the user
        boolean isStarred = userFlightDatabase.getUserFlightForUserAndFlight(userId, flightId) != null;

        // Set star icon
        saveButton.setImageResource(isStarred ? R.drawable.star_filled : R.drawable.star_outlined);

        // Add onClick listener for the star button
        saveButton.setOnClickListener(v -> {

            // Get saved user flight for this flightId
            UserFlight userFlight = userFlightDatabase.getUserFlightForUserAndFlight(userId, flightId);
            // If there is none, save the flight
            if(userFlight == null) {
                boolean result = userFlightDatabase.createUserFlight(userId, flightId);
                if(result) {
                    saveButton.setImageResource(R.drawable.star_filled);
                }
            }else {
                // If there is, delete the flight
                boolean result = userFlightDatabase.deleteUserFlight(userFlight.getId());
                if(result) {
                    saveButton.setImageResource(R.drawable.star_outlined);
                }
            }

        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);
        // Show action bar with back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize the activity
        init();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Register the back button click listener
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }

    }

}