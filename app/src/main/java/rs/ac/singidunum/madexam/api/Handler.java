package rs.ac.singidunum.madexam.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;

public class Handler {

    protected OkHttpClient client;
    protected ObjectMapper mapper;

    public Handler() {

        this.client = new OkHttpClient();
        this.mapper = new ObjectMapper();

        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

}
