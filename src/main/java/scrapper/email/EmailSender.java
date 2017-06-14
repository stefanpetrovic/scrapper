package scrapper.email;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import scrapper.model.Apartment;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.ui.freemarker.FreeMarkerTemplateUtils.processTemplateIntoString;

@Component
public class EmailSender {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration freemarkerConfig;

    private String receiver = "petrovicstefan91@gmail.com";
    private String subject = "Apartments";

    public void sendEmail(List<Apartment> apartments) throws IOException, TemplateException, MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(receiver);
        helper.setSubject(subject);

        Template template = freemarkerConfig.getTemplate("apartments.ftl");
        Map<String, Object> map = new HashMap<>();
        map.put("apartments", apartments);
        String content = processTemplateIntoString(template, map);

        helper.setText(content, true);

        mailSender.send(message);
    }
}
