package scrapper.service;

import freemarker.template.TemplateException;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import scrapper.email.SendgridMailSender;
import scrapper.extractor.HaloOglasiApartmentExtractor;
import scrapper.extractor.NekretnineRSApartmentExtractor;
import scrapper.model.Apartment;
import scrapper.model.RecommendationResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Processor {

    private static final Logger log = LoggerFactory.getLogger(Processor.class);

    @Autowired
    private HaloOglasiApartmentExtractor haloOglasiApartmentExtractor;

    @Autowired
    private NekretnineRSApartmentExtractor nekretnineRSApartmentExtractor;

    @Autowired
    private SendgridMailSender emailSender;

    @Autowired
    private ApartmentStorage apartmentStorage;

    @Autowired
    private ApartmentRecommender recommender;

    public List<Apartment> processHaloOglasi(int page) {
        Document document = haloOglasiApartmentExtractor.fetchApartmentsPage(page);

        List<Apartment> apartments = haloOglasiApartmentExtractor.extractApartmentsElements(document);

        return analizeApartments(apartments);
    }

    public List<Apartment> processNekretnineRS(int page) {
        Document document = nekretnineRSApartmentExtractor.fetchApartmentsPage(page);

        List<Apartment> apartments = nekretnineRSApartmentExtractor.extractApartmentsElements(document);

        return analizeApartments(apartments);
    }

    private List<Apartment> analizeApartments(List<Apartment> apartments) {
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

        for (int i = 1; i < 2; i++) {
            recommendedApartments.addAll(processHaloOglasi(i));
            recommendedApartments.addAll(processNekretnineRS(i));
        }

        try {
            emailSender.sendEmail(recommendedApartments);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
