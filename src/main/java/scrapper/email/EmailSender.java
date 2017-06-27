package scrapper.email;

import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
/*
    @Autowired
    private JavaMailSender mailSender;*/

    @Autowired
    private Configuration freemarkerConfig;

    private String receiver = "petrovicstefan91@gmail.com";
/*

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
    }*/
}
