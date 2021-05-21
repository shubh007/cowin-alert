package io.coderscafe.cowinalert.alert;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.coderscafe.cowinalert.config.Config;
import io.coderscafe.cowinalert.constants.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class AlertifyManager {

        @Autowired
        private EmailSender emailSender;

        @Autowired
        private Config config;

        private static final SimpleDateFormat checkDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        private void validateDataAndSendAlert(JsonObject jsonObject){
                int ageLimit = jsonObject.get(CommonConstants.MIN_AGE_LIMIT).getAsInt();
                int availableCapacityDose1 = jsonObject.get(CommonConstants.AVAILABILITY_FOR_DOSE_1).getAsInt();
                String centerName = jsonObject.get(CommonConstants.CENTER_NAME).getAsString();
                String date = jsonObject.get(CommonConstants.DATE).getAsString();
                String vaccine = jsonObject.get(CommonConstants.VACCINE_AVAILABLE).getAsString();
                Date now = new Date();
                String currTime = checkDateFormat.format(now);
                if(ageLimit == config.getAgeLimit() && availableCapacityDose1 > 0){
                        emailSender.setAlert(currTime,CommonConstants.SLOTS_AVAILABLE,centerName,date,availableCapacityDose1,ageLimit,vaccine);
                }

        }

        public void validateAndAlertify(JsonObject jsonObject){
                if (jsonObject == null){
                        return;
                }
                JsonArray jsonArray = jsonObject.getAsJsonArray(CommonConstants.SESSIONS_KEY);
                int size = jsonArray.size();
                if( size == 0){
                        return;
                }
                for (int i = 0 ; i < size ; i++){
                        validateDataAndSendAlert(jsonArray.get(i).getAsJsonObject());
                }
        }
}
