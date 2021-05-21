package io.coderscafe.cowinalert.schedule;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.coderscafe.cowinalert.Client.CowinClient;
import io.coderscafe.cowinalert.alert.AlertifyManager;
import io.coderscafe.cowinalert.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class CowinSlotCheckSchedule {



    @Autowired
    CommonUtils commonUtils;

    @Autowired
    CowinClient cowinClient;

    @Autowired
    AlertifyManager alertifyManager;

    //Checks every min
    @Scheduled(fixedRate = 1000*5)
    public void cronJobSch() throws Exception {
        List<String> datesToQuery = commonUtils.getDatesToQuery();
        for(String queryDate:datesToQuery){
           JsonObject jsonObject = cowinClient.getCowinSlotDetails(queryDate);
           System.out.println(jsonObject);
           alertifyManager.validateAndAlertify(jsonObject);
        }

    }
}
