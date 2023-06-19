package rs.ac.singidunum.madexam.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rs.ac.singidunum.madexam.api.models.FlightModel;
import rs.ac.singidunum.madexam.api.models.Pageable;

public class FlightHandler extends Handler {

    public Pageable<FlightModel> getUpcomingFlights(int page, int size) {

        String url = "https://flight.pequla.com/api/flight?page=" + page + "&size=" + size;
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

}
