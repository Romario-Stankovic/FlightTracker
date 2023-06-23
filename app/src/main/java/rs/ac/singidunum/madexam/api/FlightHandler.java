package rs.ac.singidunum.madexam.api;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rs.ac.singidunum.madexam.misc.Environment;
import rs.ac.singidunum.madexam.api.models.Flight;
import rs.ac.singidunum.madexam.api.models.Pageable;

// Flight handler that creates API calls to the flight API
public class FlightHandler extends Handler {

    // Get all upcoming flights
    public Pageable<Flight> getUpcomingFlights(int page, int size) {

        try {
            // Request URL
            String url = Environment.FLIGHT_API_URL + "/api/flight?page=" + page + "&size=" + size;
            // The request
            Request request = new Request.Builder().url(url).get().build();
            // Response
            Response response = client.newCall(request).execute();

            // Map the response to a Pageable FlightModel and return the value
            return mapper.readValue(response.body().string(), new TypeReference<Pageable<Flight>>() {});

        } catch (Exception e) {
            // In case of an error, return null
            return null;
        }

    }

    // Get upcoming flights for a specific destination
    public Pageable<Flight> getUpcomingFlightsForDestination(String destination, int page, int size) {


        try {
            // Request URL
            String url = Environment.FLIGHT_API_URL + "/api/flight/destination/" + destination + "?page=" + page + "&size=" + size;
            // The request
            Request request = new Request.Builder().url(url).get().build();
            // The response
            Response response = client.newCall(request).execute();

            // Map the response to a Pageable FlightModel and return the value
            return mapper.readValue(response.body().string(), new TypeReference<Pageable<Flight>>() {});

        } catch (Exception e) {
            // In case of an error, return null
            return null;
        }

    }

    // Get flights by their IDs
    public List<Flight> getFlightsByIDs(List<Integer> ids) {

        try {

            // Request URL
            String url = Environment.FLIGHT_API_URL + "/api/flight/list";
            // Convert the Ids list to a json object
            String json = this.mapper.writeValueAsString(ids);

            // Create a RequestBody
            RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

            // The request
            Request request = new Request.Builder().url(url).post(body).build();

            // The response
            Response response = client.newCall(request).execute();

            // Map the response to a List of FlightModels and return it
            return mapper.readValue(response.body().string(), new TypeReference<List<Flight>>() {});

        } catch (Exception e) {
            // In case of an error, return null
            return null;
        }

    }

    // Get a list of upcoming destinations
    public List<String> getAllDestinations() {

        try {
            // Request URL
            String url = Environment.FLIGHT_API_URL + "/api/flight/destination/all";
            // The request
            Request request = new Request.Builder().url(url).get().build();
            // The response
            Response response = client.newCall(request).execute();

            // Map the response to a List of strings and return it
            return mapper.readValue(response.body().string(), new TypeReference<List<String>>() {});

        } catch (Exception e) {
            // In case of an exception, return null
            return null;
        }


    }

}
