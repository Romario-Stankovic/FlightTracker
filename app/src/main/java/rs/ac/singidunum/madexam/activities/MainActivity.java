package rs.ac.singidunum.madexam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.paginate.Paginate;

import java.util.List;

import rs.ac.singidunum.madexam.R;
import rs.ac.singidunum.madexam.adapters.FlightAdapter;
import rs.ac.singidunum.madexam.api.FlightHandler;
import rs.ac.singidunum.madexam.api.models.FlightModel;
import rs.ac.singidunum.madexam.api.models.Pageable;

public class MainActivity extends AppCompatActivity {

    FlightHandler flightHandler = new FlightHandler();
    FlightAdapter adapter = new FlightAdapter(this);
    int page = 1;
    int size = 15;
    int maxPages = Integer.MAX_VALUE;

    Paginate.Callbacks callback = new Paginate.Callbacks() {
        boolean isLoading = false;
        @Override
        public void onLoadMore() {
            isLoading = true;
            Log.d("Paginate", "onLoadMore");
            page += 1;
            AsyncTask task = new AsyncTask() {
                @Override
                protected List<FlightModel> doInBackground(Object[] objects) {
                    Pageable<FlightModel> response = flightHandler.getUpcomingFlights(page - 1, size);
                    maxPages = response.getTotalPages();
                    List<FlightModel> flights = response.getContent();
                    return flights;
                }

                @Override
                protected void onPostExecute(Object o) {
                    //adapter.setData((List<FlightModel>) o);
                    adapter.addData((List<FlightModel>) o);
                    isLoading = false;
                }

            };
            task.execute();
        }

        @Override
        public boolean isLoading() {
            return isLoading;
        }

        @Override
        public boolean hasLoadedAllItems() {
            return page >= maxPages;
        }
    };

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
                //TODO: Implement logic here
            }

            if(menuItem.getItemId() == R.id.logoutMenuItem) {
                logout();
            }

            return true;
        });

        RecyclerView flightRecycleView = findViewById(R.id.fligthRecycleView);
        flightRecycleView.setAdapter(adapter);
        flightRecycleView.setLayoutManager(new LinearLayoutManager(this));

        Paginate.with(flightRecycleView, callback)
                        .setLoadingTriggerThreshold(2)
                        .addLoadingListItem(true)
                        .build();

    }

}