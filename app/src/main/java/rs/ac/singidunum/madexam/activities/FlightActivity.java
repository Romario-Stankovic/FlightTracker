package rs.ac.singidunum.madexam.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import rs.ac.singidunum.madexam.Environment;
import rs.ac.singidunum.madexam.R;

public class FlightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();

        int id = extras.getInt("id");
        String flightKey = extras.getString("flightKey");
        String flightNumber = extras.getString("flightNumber");
        String destination = extras.getString("destination");
        long scheduledAtMilis = extras.getLong("scheduledAtMilis");
        String connectedFlight = extras.getString("connectedFlight");
        String plane = extras.getString("plane");
        String gate = extras.getString("gate");
        String terminal = extras.getString("terminal");

        getSupportActionBar().setTitle(flightNumber);

        TextView flightKeyTextView = findViewById(R.id.flight_flightKeyTextView);
        TextView destinationTextView = findViewById(R.id.flight_destinationTextView);
        TextView scheduledAtTextView = findViewById(R.id.flight_scheduledAtTextView);
        TextView connectedFlightTextView = findViewById(R.id.flight_connectedFlightTextView);
        TextView planeTextView = findViewById(R.id.flight_planeTextView);
        TextView gateTextView = findViewById(R.id.flight_gateTextView);
        TextView terminalTextView = findViewById(R.id.flight_terminalTextView);
        ImageView bannerImageView = findViewById(R.id.imageView2);

        flightKeyTextView.setText(flightKey);
        destinationTextView.setText(destination);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        scheduledAtTextView.setText(dateFormat.format(new Date(scheduledAtMilis)));
        connectedFlightTextView.setText(connectedFlight != null ? connectedFlight : "N/A");
        planeTextView.setText(plane);
        gateTextView.setText(gate);
        terminalTextView.setText(terminal);

        String imageName = "/" + destination.split(" ")[0].toLowerCase() + ".jpg";

        Glide.with(this)
                .load(Environment.DESTINATION_IMAGE_URL + imageName)
                .placeholder(R.drawable.flight_placeholder)
                .into(bannerImageView);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }

    }

}