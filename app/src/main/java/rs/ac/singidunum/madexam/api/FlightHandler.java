package rs.ac.singidunum.madexam.api;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rs.ac.singidunum.madexam.Environment;
import rs.ac.singidunum.madexam.api.models.FlightModel;
import rs.ac.singidunum.madexam.api.models.Pageable;

public class FlightHandler extends Handler {

    public Pageable<FlightModel> getUpcomingFlights(int page, int size) {

        String url = Environment.FLIGHT_API_URL + "/api/flight?page=" + page + "&size=" + size;
        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();

            Pageable<FlightModel> result = mapper.readValue(response.body().string(), new TypeReference<Pageable<FlightModel>>() {});

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Pageable<FlightModel> getUpcomingFlightsForDestination(String destination, int page, int size) {

        String url = Environment.FLIGHT_API_URL + "/api/flight/destination/" + destination + "?page=" + page + "&size=" + size;
        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();

            Pageable<FlightModel> result = mapper.readValue(response.body().string(), new TypeReference<Pageable<FlightModel>>() {});

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<FlightModel> getFlightsByIDs(List<Integer> ids) {

        try {

            String url = Environment.FLIGHT_API_URL + "/api/flight/list";
            String json = this.mapper.writeValueAsString(ids);

            RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

            Request request = new Request.Builder().url(url)
                    .post(body).build();

            Response response = client.newCall(request).execute();

            List<FlightModel> result = mapper.readValue(response.body().string(), new TypeReference<List<FlightModel>>() {});

            return result;

        } catch (Exception e) {
            return null;
        }

    }

    public List<String> getAllDestinations() {

        String url = Environment.FLIGHT_API_URL + "/api/flight/destination/all";
        Request request = new Request.Builder().url(url).build();

        try {

            Response response = client.newCall(request).execute();

            List<String> result = mapper.readValue(response.body().string(), new TypeReference<List<String>>() {});

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

}
