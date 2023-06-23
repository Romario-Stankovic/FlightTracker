package rs.ac.singidunum.madexam.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import okhttp3.OkHttpClient;

// Base handler for API calls
public class Handler {

    // OkHttp client for API calls
    protected OkHttpClient client;
    // ObjectMapper to map json strings to objects
    protected ObjectMapper mapper;

    public Handler() {

        // Create the client and mapper
        this.client = new OkHttpClient();
        this.mapper = new ObjectMapper();

        // Configure the mapper to not fail on unknown field
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Create and set the date formatter
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        this.mapper.setDateFormat(dateFormat);

    }

}
