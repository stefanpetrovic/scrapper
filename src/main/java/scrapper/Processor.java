package scrapper;

import freemarker.template.TemplateException;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Processor {

    private static final Logger log = LoggerFactory.getLogger(Processor.class);

    @Autowired
    private ApartmentExtractor extractor;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private ApartmentStorage apartmentStorage;

    @Autowired
    private ApartmentRecommender recommender;

    @Scheduled(fixedDelay = 100000)
    public void processHaloOglasi() {
        Document document = extractor.fetchApartmentsPage();

        List<Apartment> apartments = extractor.extractApartmentsElements(document);

        apartmentStorage.storeApartments(apartments);

        List<Apartment> recommendedApartments = new ArrayList<>();

        for (Apartment a : apartments) {
            RecommendationResponse response = recommender.analyzeApartment(a);

            if (response.isRecommended()) {
                recommendedApartments.add(a);
            } else {
                log.info("Apartment not recommended: [url: {}, reason: {}]", a.getUrl(), response.getMessage());
            }
        }

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
