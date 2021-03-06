package scrapper.processor.extractor.html;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scrapper.model.Apartment;
import scrapper.model.ApartmentPurpose;
import scrapper.model.ApartmentSource;
import scrapper.processor.ProcessingMode;
import scrapper.processor.extractor.ApartmentExtractor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static scrapper.processor.ProcessingMode.PRODAJA;

public abstract class HTMLExtractorTemplate implements ApartmentExtractor<Document> {

    private static final Logger log = LoggerFactory.getLogger(HTMLExtractorTemplate.class);

    public final List<Apartment> extractApartmentsElements(Document document) {
        Elements apartments = getApartmentsElements(document);

        Iterator<Element> apartmentIterator = apartments.iterator();

        List<Apartment> apartmentList = new ArrayList<>();

        while (apartmentIterator.hasNext()) {
            Element el = apartmentIterator.next();

            Apartment apartment = extractApartment(el);

            if (apartment != null) {
                log.debug("Extracted apartment: [externalId: {}, url: {}]", apartment.getExternalId(), apartment.getUrl());
                apartmentList.add(apartment);
            }
        }

        log.debug("Extracted {} apartments", apartmentList.size());

        return apartmentList;
    }

    private final Apartment extractApartment(Element element) {
        String priceStr = extractPriceString(element);

        Double price = extractPrice(priceStr);

        if (price > 100) {
            String url = extractUrl(element);

            String description = extractDescription(element);

            String address = extractAddress(element);

            Double area = extractArea(element);

            Double rooms = extractRooms(element);

            String externalId = extractExternalId(element);

            String imageURL = extractImageURL(element);

            Apartment apartment = new Apartment();
            apartment.setPrice(price);
            apartment.setUrl(url);
            apartment.setDescription(description);
            apartment.setAddress(address);
            apartment.setArea(area);
            apartment.setNoOfRooms(rooms);
            apartment.setSource(getSource());
            apartment.setExternalId(externalId);
            apartment.setPurpose(getApartmentPurpose());
            apartment.setImageURL(imageURL);
            return apartment;
        }

        return null;
    }

    protected abstract Elements getApartmentsElements(Document document);

    protected abstract String extractPriceString(Element element);

    protected abstract Double extractPrice(String rawPrice);

    protected abstract String extractUrl(Element element);

    protected abstract String extractDescription(Element element);

    protected abstract String extractAddress(Element element);

    protected abstract Double extractArea(Element element);

    protected abstract Double extractRooms(Element element);

    protected abstract String extractExternalId(Element element);

    protected abstract ApartmentSource getSource();

    protected abstract ProcessingMode getProcessingMode();

    protected abstract String extractImageURL(Element element);

    private ApartmentPurpose getApartmentPurpose() {
        ProcessingMode processingMode = getProcessingMode();

        return (processingMode.equals(PRODAJA)) ? ApartmentPurpose.PRODAJA : ApartmentPurpose.IZDAVANJE;
    }
}
