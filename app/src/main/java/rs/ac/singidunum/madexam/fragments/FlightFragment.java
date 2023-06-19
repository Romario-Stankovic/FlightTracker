package rs.ac.singidunum.madexam.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lombok.NonNull;
import rs.ac.singidunum.madexam.R;
import rs.ac.singidunum.madexam.api.models.FlightModel;

public class FlightFragment extends Fragment {

    private FlightModel flight;

    public FlightFragment() {
        // Required empty public constructor
    }

    public static FlightFragment newInstance(FlightModel flight) {
        FlightFragment fragment = new FlightFragment();
        Bundle args = new Bundle();
        args.putSerializable("flight", flight);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            flight = (FlightModel) getArguments().getSerializable("flight");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flight, container, false);
    }
}