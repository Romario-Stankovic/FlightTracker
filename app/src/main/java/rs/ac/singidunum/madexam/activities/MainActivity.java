package rs.ac.singidunum.madexam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.paginate.Paginate;

import java.util.ArrayList;
import java.util.List;

import rs.ac.singidunum.madexam.R;
import rs.ac.singidunum.madexam.adapters.FlightAdapter;
import rs.ac.singidunum.madexam.api.FlightHandler;
import rs.ac.singidunum.madexam.api.models.FlightModel;
import rs.ac.singidunum.madexam.api.models.Pageable;

public class MainActivity extends AppCompatActivity {

    FlightHandler flightHandler = new FlightHandler();
    FlightAdapter adapter = new FlightAdapter(this);
    String destination = "";
    int page = 0;
    int size = 15;
    int maxPages = Integer.MAX_VALUE;

    Paginate paginate;
    RecyclerView flightRecycleView;

    Paginate.Callbacks loadPagedData = new Paginate.Callbacks() {
        private boolean isLoading = false;
        @Override
        public void onLoadMore() {
            isLoading = true;
            page += 1;
            AsyncTask task = new AsyncTask() {
                @Override
                protected List<FlightModel> doInBackground(Object[] objects) {
                    Pageable<FlightModel> response;

                    if(destination.length() == 0) {
                        response = flightHandler.getUpcomingFlights(page - 1, size);
                    } else {
                        response = flightHandler.getUpcomingFlightsForDestination(destination, page - 1, size);
                    }

                    if(response == null) {
                        maxPages = 0;
                        return new ArrayList<>();
                    }

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

    private void resetPagination() {

        if(paginate != null) {
            paginate.unbind();
        }


        adapter.clear();
        page = 0;
        maxPages = Integer.MAX_VALUE;

        paginate = Paginate.with(flightRecycleView, loadPagedData)
                .setLoadingTriggerThreshold(2)
                .addLoadingListItem(true)
                .build();

    }

    private void init() {

        flightRecycleView = findViewById(R.id.fligthRecycleView);
        flightRecycleView.setAdapter(adapter);
        flightRecycleView.setLayoutManager(new LinearLayoutManager(this));

        resetPagination();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem logoutMenuItem = menu.findItem(R.id.logoutMenuItem);
        MenuItem profileMenuItem = menu.findItem(R.id.profileMenuItem);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchView).getActionView();

        logoutMenuItem.setOnMenuItemClickListener((item) -> {
            logout();
            return true;
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                resetPagination();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                destination = newText;
                return true;
            }

        });

        return true;

    }

}