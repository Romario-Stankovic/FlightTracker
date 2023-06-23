package rs.ac.singidunum.madexam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rs.ac.singidunum.madexam.R;
import rs.ac.singidunum.madexam.adapters.FlightAdapter;
import rs.ac.singidunum.madexam.api.FlightHandler;
import rs.ac.singidunum.madexam.api.models.Flight;
import rs.ac.singidunum.madexam.database.UserFlightDatabase;
import rs.ac.singidunum.madexam.database.UserDatabase;
import rs.ac.singidunum.madexam.database.models.UserFlight;
import rs.ac.singidunum.madexam.database.models.User;

public class ProfileActivity extends AppCompatActivity {

    UserDatabase userDatabase;
    UserFlightDatabase userFlightDatabase;
    FlightHandler flightHandler;
    FlightAdapter adapter;
    SharedPreferences prefs;

    private void getSavedFlights() {

        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                // Get the userId from preferences
                int userId = prefs.getInt("userId", -1);

                // Get the list of saved flight for the logged in user
                List<UserFlight> userFlights = userFlightDatabase.getUserFlightForUser(userId);

                // List of saved flight IDs
                List<Integer> ids = new ArrayList<>();

                // Get the saved flight IDs
                for(UserFlight star : userFlights) {
                    ids.add(star.getFlightId());
                }

                // Get the flights from the API
                List<Flight> flights = flightHandler.getFlightsByIDs(ids);
                // Return the flights
                return flights;
            }

            @Override
            protected void onPostExecute(Object o) {
                // Convert the object to the list of flights
                List<Flight> flights = (List<Flight>)o;
                // Clear the adapter and add the new data
                adapter.clear();
                adapter.addData(flights);
            }

        };

        // Execute the background task
        task.execute();

    }

    private void init() {

        // Initialize references
        userDatabase = new UserDatabase(this);
        userFlightDatabase = new UserFlightDatabase(this);
        flightHandler = new FlightHandler();
        adapter = new FlightAdapter(this);
        prefs = getSharedPreferences("preferences", MODE_PRIVATE);

        // Get the userId
        int userId = prefs.getInt("userId", -1);

        // Get the user from the database
        User user = userDatabase.findUserByID(userId);

        // Get view references
        TextView usernameTextView = findViewById(R.id.a_profile_usernameData_textView);
        TextView dateOfBirthTextView = findViewById(R.id.a_profile_dateOfBirthData_textView);
        TextView genderTetView = findViewById(R.id.a_profile_genderData_textView);

        RecyclerView recyclerView = findViewById(R.id.a_profile_flights_recycleView);

        // Set the ActionBar menu
        getSupportActionBar().setTitle(user.getName() + " " + user.getLastName());

        // Initialize the recycle view
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set user data into views
        usernameTextView.setText(user.getUsername());
        dateOfBirthTextView.setText(user.getDateOfBirth());
        genderTetView.setText(user.getGender());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // Hide the activity bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize the activity
        init();
        // Get saved flights
        getSavedFlights();
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

    // Called when the activity restarts
    @Override
    public void onRestart() {
        super.onRestart();
        getSavedFlights();
    }

}