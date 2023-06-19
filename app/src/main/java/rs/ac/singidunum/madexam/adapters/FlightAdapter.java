package rs.ac.singidunum.madexam.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import rs.ac.singidunum.madexam.Environment;
import rs.ac.singidunum.madexam.R;
import rs.ac.singidunum.madexam.api.models.FlightModel;

public class FlightAdapter extends RecyclerView.Adapter {

    Context context;
    List<FlightModel> flights;

    public FlightAdapter(Context context) {
        this.context = context;
        this.flights = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.fragment_flight, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        FlightModel flight = flights.get(position);
        MyViewHolder hldr = (MyViewHolder) holder;
        hldr.flightNumberTextView.setText(flight.getFlightNumber());
        hldr.destinationTextView.setText(flight.getDestination());
        String imageName = "/" + flight.getDestination().split(" ")[0].toLowerCase() + ".jpg";

        Glide.with(hldr.view)
                .load(Environment.DESTINATION_IMAGE_URL + imageName)
                .placeholder(R.drawable.flight_placeholder)
                .into(hldr.iconImageView);
    }

    @Override
    public int getItemCount() {
        return flights.size();
    }

    public void updateData(List<FlightModel> flights) {
        this.flights.clear();
        this.flights.addAll(flights);
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView flightNumberTextView;
        TextView destinationTextView;
        ImageView iconImageView;
        View view;

        public MyViewHolder(@NotNull View itemView) {
            super(itemView);
            view = itemView;
            flightNumberTextView = itemView.findViewById(R.id.flightNumberTextView);
            destinationTextView = itemView.findViewById(R.id.destinationTextView);
            iconImageView = itemView.findViewById(R.id.iconImageView);
        }

    }

}
