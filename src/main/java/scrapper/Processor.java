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

    public List<Apartment> processHaloOglasi(int page) {
        Document document = extractor.fetchApartmentsPage(page);

        List<Apartment> apartments = extractor.extractApartmentsElements(document);

        List<Apartment> storedApartments = apartmentStorage.storeApartments(apartments);

        List<Apartment> recommendedApartments = new ArrayList<>();

        for (Apartment a : storedApartments) {
            RecommendationResponse response = recommender.analyzeApartment(a);

            boolean isRecommended = response.isRecommended();
            a.setRecommended(isRecommended);
            a.setMessage(response.getMessage());

            if (isRecommended) {
                recommendedApartments.add(a);
            } else {
                log.info("Apartment not recommended: [url: {}, reason: {}]", a.getUrl(), response.getMessage());
            }
        }

        return recommendedApartments;
    }

    @Scheduled(fixedDelay = 100000)
    public void process() {
        List<Apartment> recommendedApartments = new ArrayList<>();

        for (int i = 11; i < 21; i++) {
            recommendedApartments.addAll(processHaloOglasi(i));
        }

        try {
            emailSender.sendEmail(recommendedApartments);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
