package rs.ac.singidunum.madexam.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import rs.ac.singidunum.madexam.misc.Environment;
import rs.ac.singidunum.madexam.R;
import rs.ac.singidunum.madexam.activities.FlightActivity;
import rs.ac.singidunum.madexam.api.models.Flight;
import rs.ac.singidunum.madexam.database.UserFlightDatabase;
import rs.ac.singidunum.madexam.database.UserDatabase;

// Adapter for flight fragments
public class FlightAdapter extends RecyclerView.Adapter {

    // Adapters context
    Context context;

    // List of flights that should be sent to fragments
    List<Flight> flights;

    // Reference to userFlightDatabase
    UserFlightDatabase userFlightDatabase;

    // Reference to the userDatabase
    UserDatabase userDatabase;

    // Class that stores a reference to the fragment
    public static class FlightItemViewHolder extends RecyclerView.ViewHolder {
        // Reference to the base view
        View view;
        // Reference to the banner image
        ImageView bannerImageView;
        // Reference to the flight number text
        TextView flightTitleTextView;
        // Reference to the destination text
        TextView dateTextView;
        // Reference to the details text
        ImageButton detailsImageButton;

        // Constructor
        public FlightItemViewHolder(@NotNull View itemView) {
            super(itemView);
            // Set the values
            view = itemView;
            flightTitleTextView = itemView.findViewById(R.id.f_flight_title_textView);
            dateTextView = itemView.findViewById(R.id.f_flight_date_textView);
            bannerImageView = itemView.findViewById(R.id.f_flight_banner_imageView);
            detailsImageButton = itemView.findViewById(R.id.f_flight_details_button);
        }

    }

    // Constructor
    public FlightAdapter(Context context) {
        // Set the context
        this.context = context;
        // Initialize the list and references
        this.flights = new ArrayList<>();
        this.userFlightDatabase = new UserFlightDatabase(context);
        this.userDatabase = new UserDatabase(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the view
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_flight, parent, false);
        // Return a new FlightItemViewHolder
        return new FlightItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        // Get the flight model
        Flight flight = flights.get(position);
        // Cast the holder to a FlightItemViewHolder
        FlightItemViewHolder flightItemHolder = (FlightItemViewHolder) holder;

        // Create a onClickListener for opening the FlightActivity
        View.OnClickListener clickListener = (view) -> {
            // Create a new intent
            Intent intent = new Intent(context, FlightActivity.class);
            // Create  a bundle for flight data
            Bundle extras = new Bundle();

            // Add the flight data to the bundle
            extras.putInt("flightId", flight.getId());
            extras.putString("flightKey", flight.getFlightKey());
            extras.putString("flightNumber", flight.getFlightNumber());
            extras.putString("destination", flight.getDestination());
            extras.putLong("scheduledAtMillis", flight.getScheduledAt() != null ? flight.getScheduledAt().getTime() : 0);
            extras.putLong("estimatedAtMillis", flight.getEstimatedAt() != null ? flight.getEstimatedAt().getTime() : 0);
            extras.putString("connectedType", flight.getConnectedType());
            extras.putString("connectedFlight", flight.getConnectedFlight());
            extras.putString("plane", flight.getPlane());
            extras.putString("gate", flight.getGate());
            extras.putString("terminal", flight.getTerminal());

            // Add the bundle
            intent.putExtras(extras);
            // Start the activity
            context.startActivity(intent);
        };

        // Add the click listener on the entire view
        flightItemHolder.view.setOnClickListener(clickListener);

        // Get the banner image name (first part of the name to lower case + .jpg)
        String imageName = "/" + flight.getDestination().split(" ")[0].toLowerCase() + ".jpg";

        // Load the image into the fragment imageView using Glide
        Glide.with(flightItemHolder.view)
                .load(Environment.DESTINATION_IMAGE_URL + imageName)
                .placeholder(R.drawable.flight_placeholder)
                .into(flightItemHolder.bannerImageView);

        // Display the title
        flightItemHolder.flightTitleTextView.setText(flight.getFlightNumber() + " (" + flight.getDestination() + ")");
        // Convert the date to a string
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        // Set the date
        flightItemHolder.dateTextView.setText(df.format(flight.getScheduledAt()));

        // Add the click Listener to the button
        flightItemHolder.detailsImageButton.setOnClickListener(clickListener);
    }

    // Return the number of items
    @Override
    public int getItemCount() {
        return flights.size();
    }

    // Add the data to the list
    public void addData(List<Flight> flights) {
        this.flights.addAll(flights);
        notifyDataSetChanged();
    }

    // Clear the list
    public void clear() {
        this.flights.clear();
    }

}
