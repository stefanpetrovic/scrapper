package scrapper.email;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scrapper.model.Apartment;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.ui.freemarker.FreeMarkerTemplateUtils.processTemplateIntoString;

@Component
public class SendgridMailSender {

    private static final Logger log = LoggerFactory.getLogger(SendgridMailSender.class);

    private static final String APIKEY = "SENDGRID_API_KEY";

    private String subject = "Apartments";

    @Autowired
    private Configuration freemarkerConfig;

    public void sendEmail(List<Apartment> apartments) throws IOException, TemplateException {
        Template template = freemarkerConfig.getTemplate("apartments.ftl");
        Map<String, Object> map = new HashMap<>();
        map.put("apartments", apartments);
        String content = processTemplateIntoString(template, map);

        this.sendEmail(subject, content);


    }

    public void sendEmail(String subject, String body) {
        Email from = new Email("petrovicstefan91@gmail.com");
        Email to = new Email("petrovicstefan91@gmail.com");

        Content content = new Content("text/html", body);

        Mail mail = new Mail(from ,subject, to, content);
        String s = System.getenv(APIKEY);
        log.info(s);
        SendGrid sendGrid = new SendGrid(s);

        Request request = new Request();
        try  {
            request.method = Method.POST;
            request.endpoint = "mail/send";
            request.body = mail.build();

            Response response = sendGrid.api(request);

            log.info("Mail sent successfully");

        }catch (IOException e) {
            throw new RuntimeException("Error sending email:", e);
        }
    }
}
