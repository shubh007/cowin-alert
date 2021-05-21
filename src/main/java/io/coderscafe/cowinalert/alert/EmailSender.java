package io.coderscafe.cowinalert.alert;

import io.coderscafe.cowinalert.config.Config;
import io.coderscafe.cowinalert.constants.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class EmailSender {

    @Autowired
    private Config config;

    public void setAlert(String checkTime, String msg, String centerName, String date, int available_capacity_dose1, int min_age_limit, String vaccine) {

        Properties prop = new Properties();
        prop.put(CommonConstants.HOST_KEY, config.getMailSmtpHost());
        prop.put(CommonConstants.PORT_KEY, config.getMailSmtpPort());
        prop.put(CommonConstants.AUTH_KEY, config.getMailSmtpAuth());
        prop.put(CommonConstants.TLS_ENABLE_KEY, config.getMailSmtpStarttlsEnable());

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(config.getEmailUsername(), config.getEmailPassword());
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(config.getEmailUsername()));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(config.getToEmails())
            );
            message.setSubject("Cowin Slot Update "+msg);
            message.setText("Hey Guys,"
                    + "\n\n Slots Update for : "+date
                    + "\n\n Checked at : "+checkTime
                    +"\n\n centerName : "+centerName
                    +"\n\n min_age_limit : "+min_age_limit
                    +"\n\n vaccine : "+vaccine
                    +"\n\n available_capacity_dose1 : "+available_capacity_dose1
                    +"\n\n Please do not reply because no one will check it :p ");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
