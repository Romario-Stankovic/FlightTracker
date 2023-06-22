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
import rs.ac.singidunum.madexam.api.models.FlightModel;
import rs.ac.singidunum.madexam.database.StarDatabase;
import rs.ac.singidunum.madexam.database.UserDatabase;
import rs.ac.singidunum.madexam.database.models.StarModel;
import rs.ac.singidunum.madexam.database.models.UserModel;

public class ProfileActivity extends AppCompatActivity {

    UserDatabase userDatabase = new UserDatabase(this);
    StarDatabase starDatabase = new StarDatabase(this);
    FlightHandler flightHandler = new FlightHandler();
    FlightAdapter adapter = new FlightAdapter(this);
    private void getSavedFlights() {

        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {

                SharedPreferences prefs = getSharedPreferences("preferences", MODE_PRIVATE);

                int userId = prefs.getInt("userId", -1);

                List<StarModel> stars = starDatabase.getStarsForUser(userId);

                List<Integer> ids = new ArrayList<>();

                for(StarModel star : stars) {
                    ids.add(star.getFlightId());
                }

                List<FlightModel> flights = flightHandler.getFlightsByIDs(ids);
                return flights;
            }

            @Override
            protected void onPostExecute(Object o) {

                List<FlightModel> flights = (List<FlightModel>)o;
                adapter.clear();
                adapter.addData(flights);
            }

        };

        task.execute();

    }

    private void init() {

        SharedPreferences prefs = getSharedPreferences("preferences", MODE_PRIVATE);

        int userId = prefs.getInt("userId", -1);

        UserModel user = userDatabase.findUserByID(userId);

        TextView usernameTextView = findViewById(R.id.profile_usernameData);
        TextView dateOfBirthTextView = findViewById(R.id.profile_dateOfBirthData);
        TextView genderTetView = findViewById(R.id.profile_genderData);

        RecyclerView recyclerView = findViewById(R.id.profile_flightRecycleView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSupportActionBar().setTitle(user.getName() + " " + user.getLastName());
        usernameTextView.setText(user.getUsername());
        dateOfBirthTextView.setText(user.getDateOfBirth());
        genderTetView.setText(user.getGender());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        getSavedFlights();
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

    @Override
    public void onRestart() {
        super.onRestart();
        getSavedFlights();
    }

}