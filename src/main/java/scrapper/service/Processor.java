package scrapper.service;

import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import scrapper.email.EmailGenerator;
import scrapper.email.SendgridMailSender;
import scrapper.model.Apartment;
import scrapper.processor.ApartmentProcessor;
import scrapper.processor.ApartmentProcessorFactory;
import scrapper.processor.ProcessingMode;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static scrapper.model.ApartmentPurpose.IZDAVANJE;
import static scrapper.model.ApartmentPurpose.PRODAJA;
import static scrapper.model.ApartmentSource.HALO_OGLASI;
import static scrapper.model.ApartmentSource.NEKRETNINE_RS;

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
    private ApartmentProcessorFactory apartmentProcessorFactory;

    private ApartmentProcessor apartmentProcessorChain;

    @PostConstruct
    public void init() {
        apartmentProcessorChain = apartmentProcessorFactory.getProcessorFor(HALO_OGLASI, ProcessingMode.PRODAJA);

        ApartmentProcessor nekretnineRSProdajaProcessor = apartmentProcessorFactory.getProcessorFor(NEKRETNINE_RS, ProcessingMode.PRODAJA);
        ApartmentProcessor haloOglasiIzdavanjeProcessor = apartmentProcessorFactory.getProcessorFor(HALO_OGLASI, ProcessingMode.IZDAVANJE);
        ApartmentProcessor nekretnineRSIzdavanjeProcessor = apartmentProcessorFactory.getProcessorFor(NEKRETNINE_RS, ProcessingMode.IZDAVANJE);

        apartmentProcessorChain.setNextProcessor(nekretnineRSProdajaProcessor);
        nekretnineRSProdajaProcessor.setNextProcessor(haloOglasiIzdavanjeProcessor);
        haloOglasiIzdavanjeProcessor.setNextProcessor(nekretnineRSIzdavanjeProcessor);
    }

    @Async
    public void process() {
        List<Apartment> processedApartments = new ArrayList<>();

        for (int i = 1; i < numberOfPagesToProcess; i++) {
            processedApartments = apartmentProcessorChain.process(i);
        }

        log.info("Processed {} apartments. ", processedApartments.size());

        List<Apartment> recommendedApartments = processedApartments.stream().filter(Apartment::isRecommended).collect(Collectors.toList());

        if (recommendedApartments.isEmpty()) {
            //finish process if no apartments are to be sent via email 
            log.info("No apartments to recommend, exiting process.");
            return;
        }

        List<Apartment> apartmentsProdaja = recommendedApartments.stream().filter(e -> PRODAJA.equals(e.getPurpose())).collect(Collectors.toList());
        List<Apartment> apartmentsIzdavanje = recommendedApartments.stream().filter(e -> IZDAVANJE.equals(e.getPurpose())).collect(Collectors.toList());

        if (!apartmentsProdaja.isEmpty()) {
            sendEmail(apartmentsProdaja, "Stanovi - prodaja");
        }

        if (!apartmentsIzdavanje.isEmpty()) {
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

    //hourly rate
    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void automaticProcess() {
        log.info("Started automatic processing of apartments.");
        this.process();
    }
}
