package scrapper.service;

import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import scrapper.email.EmailGenerator;
import scrapper.email.SendgridMailSender;
import scrapper.model.Apartment;
import scrapper.processor.ApartmentProcessor;
import scrapper.processor.ApartmentProcessorChainBuilder;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static scrapper.model.ApartmentPurpose.IZDAVANJE;
import static scrapper.model.ApartmentPurpose.PRODAJA;

@Component
public class Processor {

    private static final Logger log = LoggerFactory.getLogger(Processor.class);

    @Value("${numberOfPagesToProcess}")
    private int numberOfPagesToProcess;

    @Autowired
    private SendgridMailSender emailSender;

    @Autowired
    private EmailGenerator emailGenerator;

    @Autowired
    private ApartmentProcessorChainBuilder processorChainBuilder;

    private ApartmentProcessor apartmentProcessorChain;

    @PostConstruct
    public void init() {
        apartmentProcessorChain = processorChainBuilder.build();
    }

    public void process() {
        List<Apartment> processedApartments = new ArrayList<>();

        for (int i = 0; i < numberOfPagesToProcess; i++) {
            processedApartments = apartmentProcessorChain.process(i);
        }

        log.info("Processed {} apartments. ", processedApartments.size());

        List<Apartment> recommendedApartments = processedApartments.stream().filter(Apartment::isRecommended).collect(Collectors.toList());

        if (recommendedApartments.isEmpty()) {
            log.info("No apartments to recommend, exiting process.");
            return;
        }

        List<Apartment> apartmentsProdaja = recommendedApartments.stream().filter(e -> PRODAJA.equals(e.getPurpose())).collect(Collectors.toList());
        List<Apartment> apartmentsIzdavanje = recommendedApartments.stream().filter(e -> IZDAVANJE.equals(e.getPurpose())).collect(Collectors.toList());

        if (!apartmentsProdaja.isEmpty()) {
            log.info("Notifying user about: {} new apartments for PRODAJA.", apartmentsProdaja.size());
            sendEmail(apartmentsProdaja, "Stanovi - prodaja");
        }

        if (!apartmentsIzdavanje.isEmpty()) {
            log.info("Notifying user about: {} new apartments for IZDAVANJE.", apartmentsIzdavanje.size());
            sendEmail(apartmentsIzdavanje, "Stanovi - izdavanje");
        }

        log.info("Finished processing apartments");
    }

    private void sendEmail(List<Apartment> apartments, String emailSubject) {
        try {
            String emailContent = emailGenerator.generateEmailContent(apartments);
            emailSender.sendEmail("petrovicstefan91@gmail.com", "petrovicstefan91@gmail.com", emailSubject, emailContent);
        } catch (IOException e) {
            log.error("IO error occurred while sending email.", e);
        } catch (TemplateException e) {
            log.error("Template exception occurred. ", e);
        }
    }

    @Scheduled(fixedRateString = "${processingInterval}")
    public void automaticProcess() {
        log.info("Started automatic processing of apartments.");
        this.process();
    }
}
