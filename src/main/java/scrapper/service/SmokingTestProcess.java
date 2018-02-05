package scrapper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import scrapper.email.SendgridMailSender;

@Component
public class SmokingTestProcess {

    @Autowired
    private SendgridMailSender mailSender;
/*
    @Autowired
    private*/

    @Scheduled(cron = "0 0 7-23 * * *")
    public void sendSmokingTestEmail() {
        /*mailSender.sendEmail()*/
    }
}
