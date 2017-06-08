package scrapper;

import freemarker.template.TemplateException;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@Component
public class Processor {

    @Autowired
    private ApartmentExtractor extractor;

    @Autowired
    private EmailSender emailSender;

    @Scheduled(fixedDelay = 100000)
    public void processHaloOglasi() {
        Document document = extractor.fetchApartmentsPage();

        List<Apartment> apartments = extractor.extractApartmentsElements(document);

        try {
            emailSender.sendEmail(apartments);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
