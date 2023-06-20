package rs.ac.singidunum.madexam.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import okhttp3.OkHttpClient;

public class Handler {

    protected OkHttpClient client;
    protected ObjectMapper mapper;

    public Handler() {

        this.client = new OkHttpClient();
        this.mapper = new ObjectMapper();

        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        this.mapper.setDateFormat(dateFormat);

    }

}
