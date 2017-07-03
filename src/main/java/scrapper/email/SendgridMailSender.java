package scrapper.email;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SendgridMailSender {

    private static final Logger log = LoggerFactory.getLogger(SendgridMailSender.class);

    private static final String SENDGRID_API_KEY_ENV_VAR = "SENDGRID_API_KEY";

    public boolean sendEmail(String fromAddress, String toAddress, String subject, String body) {
        Email fromEmail = new Email(fromAddress);
        Email toEmail = new Email(toAddress);
        Content content = new Content("text/html", body);

        Mail mail = new Mail(fromEmail, subject, toEmail, content);
        String apikey = System.getenv(SENDGRID_API_KEY_ENV_VAR);
        SendGrid sendGrid = new SendGrid(apikey);

        Request request = new Request();
        try {
            request.method = Method.POST;
            request.endpoint = "mail/send";
            request.body = mail.build();
            sendGrid.api(request);

            log.info("Mail sent successfully");

            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
