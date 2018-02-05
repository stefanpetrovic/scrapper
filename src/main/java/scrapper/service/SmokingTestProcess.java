package scrapper.service;

import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import scrapper.email.EmailGenerator;
import scrapper.email.SendgridMailSender;
import scrapper.model.Token;

import java.io.IOException;

@Component
public class SmokingTestProcess {

    private static final Logger log = LoggerFactory.getLogger(SmokingTestProcess.class);

    @Autowired
    private SendgridMailSender mailSender;

    @Autowired
    private EmailGenerator emailGenerator;

    @Autowired
    private TokenService tokenService;

    @Scheduled(cron = "0 0 7-23 * * *")
    public void sendSmokingTestEmail() {
        log.info("Started smoking test process");
        Token token = tokenService.generateUniqueToken();

        try {
            String email = emailGenerator.generateSmokingTestEmailContent(token);

            mailSender.sendEmail("petrovicstefan91@gmail.com", new String[] {"petrovicstefan91@gmail.com"}, "Smoke test", email);
        } catch (IOException e) {
            log.error("IOException occurred: ", e);
        } catch (TemplateException e) {
            log.error("TemplateException occurred: ", e);
        }
        log.info("Finished smoking test process");
    }
}
