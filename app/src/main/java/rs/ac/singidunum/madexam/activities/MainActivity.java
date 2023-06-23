package rs.ac.singidunum.madexam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.paginate.Paginate;

import java.util.ArrayList;
import java.util.List;

import rs.ac.singidunum.madexam.R;
import rs.ac.singidunum.madexam.adapters.FlightAdapter;
import rs.ac.singidunum.madexam.api.FlightHandler;
import rs.ac.singidunum.madexam.api.models.Flight;
import rs.ac.singidunum.madexam.api.models.Pageable;

public class MainActivity extends AppCompatActivity {

    FlightHandler flightHandler;
    FlightAdapter adapter;
    String destination = "";
    int page = 0;
    int size = 15;
    int maxPages = Integer.MAX_VALUE;

    Paginate paginate;
    RecyclerView flightRecycleView;
    SharedPreferences prefs;

    // Callback when data paged data needs to be loaded
    Paginate.Callbacks loadPagedData = new Paginate.Callbacks() {
        private boolean isLoading = false;
        // Called when more data needs to be loaded
        @Override
        public void onLoadMore() {
            isLoading = true;
            page += 1;
            AsyncTask task = new AsyncTask() {
                @Override
                protected List<Flight> doInBackground(Object[] objects) {
                    Pageable<Flight> response;

                    // If the destination is not provided, load all data
                    if(destination.trim().length() == 0) {
                        response = flightHandler.getUpcomingFlights(page - 1, size);
                    } else {
                        // Else load data for that destination
                        response = flightHandler.getUpcomingFlightsForDestination(destination, page - 1, size);
                    }

                    // If there is no response, set max pages to 0 and don't show data
                    if(response == null) {
                        maxPages = 0;
                        return new ArrayList<>();
                    }

                    // Set max pages from paginate object
                    maxPages = response.getTotalPages();
                    // Get the list of flights
                    List<Flight> flights = response.getContent();

                    // Return the list of flights
                    return flights;
                }

                @Override
                protected void onPostExecute(Object o) {
                    // Add the data to the adapter
                    adapter.addData((List<Flight>) o);
                    isLoading = false;
                }

            };
            // Execute the async task
            task.execute();
        }

        // Is loading in progress
        @Override
        public boolean isLoading() {
            return isLoading;
        }

        // Has loaded all data
        @Override
        public boolean hasLoadedAllItems() {
            return page >= maxPages;
        }
    };

    // Open the login activity and replace the current one
    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(intent);
        this.finish();
    }

    // Open the profile activity
    private void openProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    // Logout
    private void logout() {

        // Get shared prefs editor
        SharedPreferences.Editor editor = prefs.edit();

        // Set the userId to -1
        editor.putInt("userId", -1);
        // Apply the changes
        editor.apply();

        // Display a message to the user
        Toast.makeText(this, "You have logged out", Toast.LENGTH_SHORT).show();

        // Open login activity
        openLoginActivity();

    }

    private void resetPagination() {

        // Unbind previous paginate object
        if(paginate != null) {
            paginate.unbind();
        }

        // Clear the adapter and reset page values
        adapter.clear();
        page = 0;
        maxPages = Integer.MAX_VALUE;

        // Create a paginate object
        paginate = Paginate.with(flightRecycleView, loadPagedData)
                .setLoadingTriggerThreshold(2)
                .addLoadingListItem(true)
                .build();

    }

    private void init() {

        // Set references
        adapter = new FlightAdapter(this);
        flightHandler = new FlightHandler();
        prefs = getApplicationContext().getSharedPreferences("preferences", MODE_PRIVATE);

        // Get views
        flightRecycleView = findViewById(R.id.a_main_fligths_recycleView);

        // Set recycle view adapter and layout manager
        flightRecycleView.setAdapter(adapter);
        flightRecycleView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the view
        init();

        // Reset pagination data
        resetPagination();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Get the menu items and search view
        MenuItem logoutMenuItem = menu.findItem(R.id.m_main_logout_menuItem);
        MenuItem profileMenuItem = menu.findItem(R.id.m_main_profile_menuItem);
        SearchView searchView = (SearchView) menu.findItem(R.id.m_main_search_searchView).getActionView();

        // Set profile menu item click listener
        profileMenuItem.setOnMenuItemClickListener((item) -> {
            openProfileActivity();
            return true;
        });

        // Set logout menu item click listener
        logoutMenuItem.setOnMenuItemClickListener((item) -> {
            logout();
            return true;
        });

        // Set SearchView query listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                resetPagination();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                destination = newText;
                if(destination.trim().length() == 0) {
                    resetPagination();
                }
                return true;
            }

        });

        return true;

    }

}