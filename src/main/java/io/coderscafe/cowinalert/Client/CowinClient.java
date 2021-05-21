package io.coderscafe.cowinalert.Client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.coderscafe.cowinalert.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Component
public class CowinClient {

    @Autowired
    Config config;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    HttpEntity<String> httpEntity;

    @Autowired
    Gson gson;

    public JsonObject getCowinSlotDetails(String date){
        String cowinUrl = config.getCowinUrl() + "?pincode=" +
                config.getPincode() +
                "&date=" +
                date;
        ResponseEntity<String> result = null;
        try {
             result = restTemplate.exchange(cowinUrl, HttpMethod.GET, httpEntity, String.class);
        }catch (Exception e){
            System.out.println("Ignore and Try Again");
        }
        if(result != null && result.getStatusCode() == HttpStatus.OK){
            String resultBody = result.getBody();
            return gson.fromJson( resultBody, JsonObject.class);
        }
        return null;
    }

}
