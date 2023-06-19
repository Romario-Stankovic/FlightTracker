package rs.ac.singidunum.madexam.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import rs.ac.singidunum.madexam.R;
import rs.ac.singidunum.madexam.api.models.FlightModel;

public class FlightAdapter extends RecyclerView.Adapter {

    Context context;
    List<FlightModel> flights;

    public FlightAdapter(Context context, List<FlightModel> flights) {
        this.context = context;
        this.flights = flights;
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

        public MyViewHolder(@NotNull View itemView) {
            super(itemView);
            flightNumberTextView = itemView.findViewById(R.id.flightNumberTextView);
            destinationTextView = itemView.findViewById(R.id.destinationTextView);
        }

    }

}
