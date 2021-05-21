package io.coderscafe.cowinalert.utils;

import io.coderscafe.cowinalert.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class CommonUtils {

    @Autowired
    private Config config;

    private static final SimpleDateFormat queryDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public List<String> getDatesToQuery(){
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        List<String> queryDates = new ArrayList<>();
        for(int i = 0; i < config.getNoOfDaysLimit() ; i++){
            c.add(Calendar.DATE, 1);
            queryDates.add(queryDateFormat.format(c.getTime()));
        }
        return queryDates;
    }
}
