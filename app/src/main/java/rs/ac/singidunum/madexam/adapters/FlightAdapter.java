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

import java.util.ArrayList;
import java.util.List;

import rs.ac.singidunum.madexam.Environment;
import rs.ac.singidunum.madexam.R;
import rs.ac.singidunum.madexam.activities.FlightActivity;
import rs.ac.singidunum.madexam.api.models.FlightModel;
import rs.ac.singidunum.madexam.database.StarDatabase;
import rs.ac.singidunum.madexam.database.UserDatabase;

public class FlightAdapter extends RecyclerView.Adapter {

    Context context;
    List<FlightModel> flights;
    StarDatabase starHelper;
    UserDatabase userHelper;

    // Class that stores a reference to the fragment
    public static class FlightItemViewHolder extends RecyclerView.ViewHolder {
        // Reference to the base view
        View view;
        // Reference to the banner image
        ImageView bannerIconImage;
        // Reference to the flight number text
        TextView flightNumberTextView;
        // Reference to the destination text
        TextView destinationTextView;
        // Reference to the details text
        ImageButton detailsImageButton;

        // Constructor
        public FlightItemViewHolder(@NotNull View itemView) {
            super(itemView);
            // Set the values
            view = itemView;
            flightNumberTextView = itemView.findViewById(R.id.flightNumberTextView);
            destinationTextView = itemView.findViewById(R.id.destinationTextView);
            bannerIconImage = itemView.findViewById(R.id.iconImageView);
            detailsImageButton = itemView.findViewById(R.id.detailsImageButton);
        }

    }

    public FlightAdapter(Context context) {
        this.context = context;
        this.flights = new ArrayList<>();
        this.starHelper = new StarDatabase(context);
        this.userHelper = new UserDatabase(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_flight, parent, false);
        return new FlightItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        FlightModel flight = flights.get(position);
        FlightItemViewHolder item = (FlightItemViewHolder) holder;

        View.OnClickListener clickListener = (view) -> {
            Intent intent = new Intent(context, FlightActivity.class);
            Bundle extras = new Bundle();

            extras.putInt("flightId", flight.getId());
            extras.putString("flightKey", flight.getFlightKey());
            extras.putString("flightNumber", flight.getFlightNumber());
            extras.putString("destination", flight.getDestination());
            extras.putLong("scheduledAtMilis", flight.getScheduledAt() != null ? flight.getScheduledAt().getTime() : 0);
            extras.putLong("estimatedAtMilis", flight.getEstimatedAt() != null ? flight.getEstimatedAt().getTime() : 0);
            extras.putString("connectedType", flight.getConnectedType());
            extras.putString("connectedFlight", flight.getConnectedFlight());
            extras.putString("plane", flight.getPlane());
            extras.putString("gate", flight.getGate());
            extras.putString("terminal", flight.getTerminal());

            intent.putExtras(extras);
            context.startActivity(intent);
        };

        item.view.setOnClickListener(clickListener);

        String imageName = "/" + flight.getDestination().split(" ")[0].toLowerCase() + ".jpg";
        Glide.with(item.view)
                .load(Environment.DESTINATION_IMAGE_URL + imageName)
                .placeholder(R.drawable.flight_placeholder)
                .into(item.bannerIconImage);

        item.flightNumberTextView.setText(flight.getFlightNumber());
        item.destinationTextView.setText(flight.getDestination());

        item.detailsImageButton.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return flights.size();
    }

    public void addData(List<FlightModel> flights) {
        this.flights.addAll(flights);
        notifyDataSetChanged();
    }

    public void clear() {
        this.flights.clear();
    }

}
